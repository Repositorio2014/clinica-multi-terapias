import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { FaIconComponent } from '@fortawesome/angular-fontawesome';

@Component({
  selector: 'jhi-home',
  standalone: true,
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
  imports: [RouterLink, FaIconComponent],
})
export default class HomeComponent {
  pacientesCount = 0;
  agendasCount = 0;
  profissionaisCount = 0;
  salasCount = 0;
}
