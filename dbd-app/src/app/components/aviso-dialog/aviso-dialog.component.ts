import { Component } from '@angular/core';
import {
  MatDialogActions,
  MatDialogClose,
  MatDialogContent,
  MatDialogTitle,
} from '@angular/material/dialog';

import {MatButtonModule} from '@angular/material/button';


@Component({
  selector: 'app-aviso-dialog',
  standalone: true,
  imports: [MatDialogTitle, MatDialogContent, MatDialogActions, MatDialogClose, MatButtonModule],
  templateUrl: './aviso-dialog.component.html',
  styleUrl: './aviso-dialog.component.css'
})
export class AvisoDialogComponent {

}
