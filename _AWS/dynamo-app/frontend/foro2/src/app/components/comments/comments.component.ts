import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Comment } from 'src/app/model/comment';
import { Foro } from 'src/app/model/foro';
import { CommentService } from 'src/app/service/comment.service';
import { ForoService } from 'src/app/service/foro.service';

@Component({
  selector: 'app-comments',
  templateUrl: './comments.component.html',
  styleUrls: ['./comments.component.css']
})
export class CommentsComponent {

  foro = new Foro();
  commentarios: Comment[] = [];
  insertComment = false;
  loading = true;

  constructor(private activatedRoute: ActivatedRoute,
      private foroService: ForoService,
      private commentService: CommentService,
      private route: Router) {

    activatedRoute.params.subscribe( params => {
      this.foroService.getForo(params["id"]).subscribe(f=> this.foro = f);
      this.commentService.getComments(params["id"]).subscribe( c => {
        this.commentarios = c;
        this.loading = false;
      });
    });
  }

  onNuevo(): void {
    this.insertComment = !this.insertComment;
  }

  home(): void {
    this.route.navigate(["/home"]);
  }
}
