import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { ISala } from '../sala.model';

@Component({
  standalone: true,
  selector: 'jhi-sala-detail',
  templateUrl: './sala-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class SalaDetailComponent {
  sala = input<ISala | null>(null);

  previousState(): void {
    window.history.back();
  }
}
