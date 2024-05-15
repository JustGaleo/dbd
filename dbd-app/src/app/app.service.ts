import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AppService {

  constructor(private http: HttpClient) { }

  getRandomMap() {
      const url = 'http://localhost:8080/mapCache/getRandom';
      return this.http.get<any>(url);
  }

  getImageMap(nombre: string){
    const url = 'http://localhost:8080/mapCat/uploads/imgName/'+nombre;
    return this.http.get(url, { responseType: 'blob' });
  }

  getRandomPerks() {
    const url = 'http://localhost:8080/perkCat/getRandom/4';
    return this.http.get<any>(url);
}

getRandomCastigo() {
  const url = 'http://localhost:8080/castigoCache/getRandom';
  return this.http.get<any>(url);
}

getImageCastigo(nombre: string){
  const url = 'http://localhost:8080/castigoCat/uploads/imgName/'+nombre;
  return this.http.get(url, { responseType: 'blob' });
}

transferirCastigos(){
  const url = 'http://localhost:8080/castigoCache/transferir';
  return this.http.get<any>(url);
}

transferirMapas(){
  const url = 'http://localhost:8080/mapCache/transferir';
  return this.http.get<any>(url);
}

getImagePerk(nombre: string){
  const url = 'http://localhost:8080/perkCat/uploads/imgName/'+nombre;
  return this.http.get(url, { responseType: 'blob' });
}

getMapCat(){
  const url = 'http://localhost:8080/mapCat';
  return this.http.get<any>(url);
}

getRondas(){
  const url = 'http://localhost:8080/ronda';
  return this.http.get<any>(url);
}

modifyRonda(id : number){
  const url = 'http://localhost:8080/ronda/modify/'+id;
  return this.http.get<any>(url);
}

createRonda(){
  const url = 'http://localhost:8080/ronda/create';
  return this.http.get<any>(url);
}

getPartidaByRondaId(ronda : number){
  const url = 'http://localhost:8080/partida/byRonda/'+ronda;
  return this.http.get<any>(url);
}

createPartida(idMapa: string, idRonda: number, status: string) {
  const url = 'http://localhost:8080/partida';
  const body = { idMapa, idRonda, status};
  return this.http.post<any>(url, body);
}

countMapas(){
  const url = 'http://localhost:8080/mapCat/count';
  return this.http.get<any>(url);
}


}
