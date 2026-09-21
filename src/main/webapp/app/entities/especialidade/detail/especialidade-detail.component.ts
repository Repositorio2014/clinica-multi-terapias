import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { IEspecialidade } from '../especialidade.model';

@Component({
  standalone: true,
  selector: 'jhi-especialidade-detail',
  templateUrl: './especialidade-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class EspecialidadeDetailComponent {
  especialidade = input<IEspecialidade | null>(null);

  previousState(): void {
    window.history.back();
  }
}
