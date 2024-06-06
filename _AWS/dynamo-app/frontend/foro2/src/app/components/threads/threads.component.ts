import { Component } from '@angular/core';
import { ForoService } from '../../service/foro.service';
import { Foro } from 'src/app/model/foro';

@Component({
  selector: 'app-threads',
  templateUrl: './threads.component.html',
  styleUrls: ['./threads.component.css']
})
export class ThreadsComponent {

  foros: Foro[] = [];

  insertForo = false;
  loading = true;

  constructor(private foroService: ForoService) {

  }

   ngOnInit(): void {
    this.foroService.getForos().subscribe( c => {
      this.foros = c;
      this.loading = false;
    });
   }

   onNuevo(): void {
    this.insertForo = !this.insertForo;
   }

}
