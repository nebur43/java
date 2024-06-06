import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Foro } from 'src/app/model/foro';
import { ForoService } from 'src/app/service/foro.service';

@Component({
  selector: 'app-new-thread',
  templateUrl: './new-thread.component.html',
  styleUrls: ['./new-thread.component.css']
})
export class NewThreadComponent {

  titulo = "";
  usuario = "";
  error = "";
  loading=false;

  constructor(private foroService: ForoService, private router: Router) {
    const u = localStorage.getItem('usuario');
    if (u!=null) {
      this.usuario = u;
    }
  }

  onCrear() {
    

    if (this.titulo.length<5) {
      this.error = "Titulo vacío o muy corto";
      return;
    }
    if (this.usuario.length<3) {
      this.error = "Nombre vacío o muy corto";
      return;
    }
    this.loading=true;
    localStorage.setItem('usuario', this.usuario);
    const foro = new Foro();
    foro.asunto = this.titulo;
    foro.usuario = this.usuario;
    this.foroService.createForo(foro).subscribe(x => {
      //this.router.navigate(['/home']);
      window.location.reload();
    });
  }

}
