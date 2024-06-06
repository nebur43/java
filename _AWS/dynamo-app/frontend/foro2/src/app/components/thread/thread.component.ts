import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { Foro } from 'src/app/model/foro';

@Component({
  selector: 'app-thread',
  templateUrl: './thread.component.html',
  styleUrls: ['./thread.component.css']
})
export class ThreadComponent {

  @Input() foro= new Foro();
  
  constructor(private router: Router) {

  }

  ngOnInit(): void {
    //Called after the constructor, initializing input properties, and the first call to ngOnChanges.
    //Add 'implements OnInit' to the class.
  }

  onClick() {
    this.router.navigate(['/comments',this.foro.dateForo]);
  }
}
