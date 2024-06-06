import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { Comment } from 'src/app/model/comment';
import { CommentService } from 'src/app/service/comment.service';

@Component({
  selector: 'app-new-comment',
  templateUrl: './new-comment.component.html',
  styleUrls: ['./new-comment.component.css']
})
export class NewCommentComponent {
  
  @Input() dateForo = 0;
  error= "";
  comentario="";
  usuario="";
  loading=false;

  constructor(private commentService: CommentService, private router: Router) {
    const u = localStorage.getItem('usuario');
    if (u!=null) {
      this.usuario = u;
    }
  }

  onCrear(): void {
    if (this.comentario.length<5) {
      this.error = "Comentario vacío o muy corto";
      return;
    }
    if (this.usuario.length<3) {
      this.error = "Nombre vacío o muy corto";
      return;
    }
    this.loading = true;
    localStorage.setItem('usuario', this.usuario);
    const comment = new Comment();
    comment.comentario = this.comentario;
    comment.usuario = this.usuario;
    comment.dateForo = this.dateForo;
    this.commentService.createComment(comment).subscribe(x => {
      //this.router.navigate(['/home']);
      window.location.reload();
    });
  }
}
