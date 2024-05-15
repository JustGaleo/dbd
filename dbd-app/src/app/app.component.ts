import { Component, AfterViewInit, OnInit } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { AppService } from './app.service';
import { MatButtonModule } from '@angular/material/button';
import { DialogComponent } from './components/dialog/dialog.component';
import { AvisoDialogComponent } from './components/aviso-dialog/aviso-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import { MatDialogModule } from '@angular/material/dialog';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterLink, RouterLinkActive, MatButtonModule, MatDialogModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements AfterViewInit, OnInit {
  title = 'dbd-app';
  backgroundImage = '';
  imageToShow = {} as { imagen: any, nombre: string };
  castigoToShow = {} as { imagen: any, nombre: string };
  imageToShowCastigo = {} as { imagen: any, nombre: string };
  isImageLoading = false;
  nombreMapa = '';
  idMapa = 0;
  perks = [] as { id: number, nombre: string }[];
  tablero = [] as { imagen: any, nombre: string }[];
  imageToShowPerks = [] as { imagen: any, nombre: string }[];
  idRonda = -1;
  enableIniciarPartida = true;
  rows = 0;
  columns = 5;



  constructor(private app: AppService, public dialog: MatDialog) {
  }

  async ngOnInit(): Promise<void> {
    this.setRandomBackground();

  }

  async ngAfterViewInit(): Promise<void> {
    await this.validacionRondas();
  }


  async validacionRondas(): Promise<void> {
    var rondas = await this.getRondas();
    console.log(rondas);
    var rondaLength = rondas.length;
    if (rondaLength == 0) {
      console.log("No hay rondas registradas: debemos crear una nueva");
      var ronda = await this.createRonda();
      this.idRonda = ronda.id;
      console.log(ronda);
    } else {
      console.log("Ya existen rondas registradas: validemos la ultima");
      var statusRonda = rondas[rondaLength - 1].fin;
      if (!statusRonda) {
        console.log('No ha terminado la ultima ronda');
        this.idRonda = rondas[rondaLength - 1].id;
      } else {
        console.log('Ya termino la ultima ronda, creamos una nueva');
        var ronda = await this.createRonda();
        this.idRonda = ronda.id;
      }
    }
  }

  async validacionPartidas(): Promise<void> {
    this.tablero = [];
    var partidas = await this.getPartidasByRonda(this.idRonda);
    const nameArray: { nombre: string, status: string }[] = partidas.map(item => ({ nombre: item.idMapa, status: item.status }));
    console.log(nameArray);
    this.getImageFromTablero(nameArray);

  }

  createImageFromBlobTablero(image: Blob, index: number, name: string) {
    let reader = new FileReader();
    reader.addEventListener("load", () => {
      this.tablero.push({ imagen: reader.result, nombre: name });
    }, false);
    if (image) {
      reader.readAsDataURL(image);
    }
  }

  getImageFromTablero(names: any[]) {
    this.isImageLoading = true;
    this.tablero = [];
    names.forEach((value, index) => {
      console.log(value.nombre);
      this.app.getImageMap(value.nombre).subscribe(data => {
        this.createImageFromBlobTablero(data, index, value.status);
        if (index === names.length - 1) {
          this.isImageLoading = false;
        }
      });
    });
  }

  createImageFromBlob(image: Blob, name: string) {
    let reader = new FileReader();
    reader.addEventListener("load", () => {
      this.imageToShow.imagen = reader.result;
      this.imageToShow.nombre = name;
    }, false);
    if (image) {
      reader.readAsDataURL(image);
    }
  }

  getImageFromService(name: string) {
    this.isImageLoading = true;
    this.app.getImageMap(name).subscribe(data => {
      this.createImageFromBlob(data, name);
      this.isImageLoading = false;
    });
  }


  getPartidasByRonda(ronda: number): Promise<any[]> {
    return new Promise<any[]>((resolve, reject) => {
      this.app.getPartidaByRondaId(ronda).subscribe(
        data => {
          resolve(data);
        },
        error => {
          reject(error);
        }
      );
    });
  }

  getRondas(): Promise<any[]> {
    return new Promise<any[]>((resolve, reject) => {
      this.app.getRondas().subscribe(
        data => {
          resolve(data);
        },
        error => {
          reject(error);
        }
      );
    });
  }

  createRonda(): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      this.app.createRonda().subscribe(
        data => {
          resolve(data);
        },
        error => {
          reject(error);
        }
      );
    });
  }

  setRandomBackground(): void {
    const randomIndex = 1 + Math.floor(Math.random() * 14);
    this.backgroundImage = `url('/assets/background/image${randomIndex}.jpg')`;
  }


  createImageFromBlobPerk(image: Blob, index: number, name: string) {
    let reader = new FileReader();
    reader.addEventListener("load", () => {
      this.imageToShowPerks.push({ imagen: reader.result, nombre: name });
    }, false);
    if (image) {
      reader.readAsDataURL(image);
    }
  }

  getImageFromPerks(names: string[]) {
    this.isImageLoading = true;
    this.imageToShowPerks = [];
    names.forEach((name, index) => {
      this.app.getImagePerk(name).subscribe(data => {
        this.createImageFromBlobPerk(data, index, name);
        if (index === names.length - 1) {
          this.isImageLoading = false;
        }
      });
    });
  }

  async bolsaMapas(): Promise<void> {
    console.log('Vamos a sacar Mapas');
    var mapa = await this.sacarMapa();
    if (mapa.nombre != 'NULO') {
      console.log('Si hay mapas');
      this.nombreMapa = mapa.nombre;
      this.getImageFromService(mapa.nombre);
      this.enableIniciarPartida = false;
    } else {
      console.log('Se terminaron los mapas');
      await this.openDialog();
      console.log('Empezemos a actualizar todo!');
      this.tablero = [];
      //PRIMERO, TENEMOS QUE MIGRAR LOS MAPAS A CACHE
      await this.transferirMapas();
      //SEGUNDO, ACTUALIZAMOS NUESTRA ULTIMA RONDA A CONCLUIDA
      await this.actualizarRonda(this.idRonda);
      //TERCERO, CREAMOS UNA NUEVA RONDA Y GUARDAMOS SU ID, PODEMOS LLAMAR EL METODO QUE YA HICE
      await this.validacionRondas();
    }
  }

  sacarMapa(): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      this.app.getRandomMap().subscribe(
        data => {
          resolve(data);
        },
        error => {
          reject(error);
        }
      );
    });
  }

  bolsaPerks(): void {
    console.log('Vamos a sacar Perks');
    this.perks = [];
    this.app.getRandomPerks().subscribe(resp => {
      this.perks = resp;
      const nameArray: string[] = this.perks.map(item => item.nombre);
      this.getImageFromPerks(nameArray);
    })
    console.log(this.imageToShowPerks)
  }

  
  async bolsaCastigos(): Promise<void> {
    console.log('Vamos a sacar castigos');
    var validacion = 0;
    while(validacion == 0){
      var castigo = await this.sacarCastigo();
      console.log(castigo)
      if (castigo.nombre != 'NULO') {
        this.getImageFromCastigo(castigo.nombre);
        validacion = 1;
      } else {
        console.log('Se terminaron los castigos');
        var response = await this.transferirCastigos();
      }
    }
    
  }

  createImageFromCastigo(image: Blob, name: string) {
    let reader = new FileReader();
    reader.addEventListener("load", () => {
      this.castigoToShow.imagen = reader.result;
      this.castigoToShow.nombre = name;
    }, false);
    if (image) {
      reader.readAsDataURL(image);
    }
  }

  getImageFromCastigo(name: string) {
    this.isImageLoading = true;
    this.app.getImageCastigo(name).subscribe(data => {
      this.createImageFromCastigo(data, name);
      this.isImageLoading = false;
    });
  }

  sacarCastigo(): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      this.app.getRandomCastigo().subscribe(
        data => {
          resolve(data);
        },
        error => {
          reject(error);
        }
      );
    });
  }

  transferirCastigos(): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      this.app.transferirCastigos().subscribe(
        data => {
          resolve(data);
        },
        error => {
          reject(error);
        }
      );
    });
  }

  transferirMapas(): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      this.app.transferirMapas().subscribe(
        data => {
          resolve(data);
        },
        error => {
          reject(error);
        }
      );
    });
  }

    actualizarRonda(id : number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      this.app.modifyRonda(id).subscribe(
        data => {
          resolve(data);
        },
        error => {
          reject(error);
        }
      );
    });
  }

  async empezarPartida(): Promise<void> {
    console.log('A ganar');
    var statusPartida = await this.sacarEstatusPartida();
    console.log(statusPartida, ' en el mapa ', this.nombreMapa);
    this.enableIniciarPartida = true;
    this.imageToShow.imagen = '';
    this.imageToShow.nombre = '';
    this.imageToShowCastigo.imagen = '';
    this.imageToShowCastigo.nombre = '';
    this.imageToShowPerks = [];
    this.castigoToShow.imagen = '';
    this.castigoToShow.nombre = '';
    var partida = await this.createPartida(this.nombreMapa, this.idRonda, statusPartida);
    console.log(partida);
    await this.validacionPartidas();
  }

  sacarEstatusPartida(): Promise<any> {

    return new Promise<any>((resolve, reject) => {
      this.dialog.open(DialogComponent).afterClosed().subscribe(
        data => {
          resolve(data);
        },
        error => {
          reject(error);
        }
      );
    });

  }

  openDialog(): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      this.dialog.open(AvisoDialogComponent).afterClosed().subscribe(
        data => {
          resolve(data);
        },
        error => {
          reject(error);
        }
      );
    });
  }

  createPartida(idMapa: string, idRonda: number, status: string): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      this.app.createPartida(idMapa, idRonda, status).subscribe(
        data => {
          resolve(data);
        },
        error => {
          reject(error);
        }
      );
    });

  }

  countMaps(): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      this.app.countMapas().subscribe(
        data => {
          resolve(data);
        },
        error => {
          reject(error);
        }
      );
    });
  }

  getRowsData() {
    const chunkSize = 5; // Number of items per row
    const rows = [];
    for (let i = 0; i < this.tablero.length; i += chunkSize) {
      rows.push(this.tablero.slice(i, i + chunkSize));
    }
    return rows;
  }

}
