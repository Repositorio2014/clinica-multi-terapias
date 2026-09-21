import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { IProfissional } from '../profissional.model';

@Component({
  standalone: true,
  selector: 'jhi-profissional-detail',
  templateUrl: './profissional-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class ProfissionalDetailComponent {
  profissional = input<IProfissional | null>(null);

  previousState(): void {
    window.history.back();
  }
}
