import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { IAgenda } from '../agenda.model';

@Component({
  standalone: true,
  selector: 'jhi-agenda-detail',
  templateUrl: './agenda-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class AgendaDetailComponent {
  agenda = input<IAgenda | null>(null);

  previousState(): void {
    window.history.back();
  }
}
