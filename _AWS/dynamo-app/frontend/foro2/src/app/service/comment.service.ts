import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Comment } from '../model/comment';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  constructor(private httpClient: HttpClient) { }

  public getComments(dateForo: number): Observable<Comment[]> {
    return this.httpClient.get<Comment[]>(environment.endpoint + '/Prod/comment/'+dateForo);
  }

  public createComment(comment: Comment): Observable<Comment> {
    return this.httpClient.put<Comment>(environment.endpoint +'/Prod/comment',comment);
  }
  
}
