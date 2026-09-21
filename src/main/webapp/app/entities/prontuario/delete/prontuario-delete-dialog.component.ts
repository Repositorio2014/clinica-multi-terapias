import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IProntuario } from '../prontuario.model';
import { ProntuarioService } from '../service/prontuario.service';

@Component({
  standalone: true,
  templateUrl: './prontuario-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ProntuarioDeleteDialogComponent {
  prontuario?: IProntuario;

  protected prontuarioService = inject(ProntuarioService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.prontuarioService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
