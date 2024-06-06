import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ThreadsComponent } from './components/threads/threads.component';
import { CommentsComponent } from './components/comments/comments.component';

const routes: Routes = [
  { path: 'home', component: ThreadsComponent},
  { path: 'comments/:id', component: CommentsComponent},
  { path: '**', pathMatch: 'full', redirectTo: 'home'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
