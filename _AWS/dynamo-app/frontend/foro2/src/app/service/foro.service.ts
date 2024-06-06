import { Injectable } from '@angular/core';
import { Foro } from '../model/foro';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ForoService {

  constructor(private httpClient: HttpClient) { 

  }

  public getForos(): Observable<Foro[]> {
    return this.httpClient.get<Foro[]>(environment.endpoint +'/Prod/foro');
  }

  public getForo(id: number): Observable<Foro> {
    return this.httpClient.get<Foro>(environment.endpoint +'/Prod/foro/'+id);
  }

  public createForo(foro: Foro): Observable<Foro> {
    return this.httpClient.put<Foro>(environment.endpoint +'/Prod/foro',foro);
  }
}
