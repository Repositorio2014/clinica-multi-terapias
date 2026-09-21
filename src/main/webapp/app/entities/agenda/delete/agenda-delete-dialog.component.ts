import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IAgenda } from '../agenda.model';
import { AgendaService } from '../service/agenda.service';

@Component({
  standalone: true,
  templateUrl: './agenda-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class AgendaDeleteDialogComponent {
  agenda?: IAgenda;

  protected agendaService = inject(AgendaService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.agendaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
