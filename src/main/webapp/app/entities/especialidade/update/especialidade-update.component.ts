import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IProfissional } from 'app/entities/profissional/profissional.model';
import { ProfissionalService } from 'app/entities/profissional/service/profissional.service';
import { TipoEspecialidade } from 'app/entities/enumerations/tipo-especialidade.model';
import { EspecialidadeService } from '../service/especialidade.service';
import { IEspecialidade } from '../especialidade.model';
import { EspecialidadeFormGroup, EspecialidadeFormService } from './especialidade-form.service';

@Component({
  standalone: true,
  selector: 'jhi-especialidade-update',
  templateUrl: './especialidade-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class EspecialidadeUpdateComponent implements OnInit {
  isSaving = false;
  especialidade: IEspecialidade | null = null;
  tipoEspecialidadeValues = Object.keys(TipoEspecialidade);

  profissionalsSharedCollection: IProfissional[] = [];

  protected especialidadeService = inject(EspecialidadeService);
  protected especialidadeFormService = inject(EspecialidadeFormService);
  protected profissionalService = inject(ProfissionalService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: EspecialidadeFormGroup = this.especialidadeFormService.createEspecialidadeFormGroup();

  compareProfissional = (o1: IProfissional | null, o2: IProfissional | null): boolean =>
    this.profissionalService.compareProfissional(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ especialidade }) => {
      this.especialidade = especialidade;
      if (especialidade) {
        this.updateForm(especialidade);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const especialidade = this.especialidadeFormService.getEspecialidade(this.editForm);
    if (especialidade.id !== null) {
      this.subscribeToSaveResponse(this.especialidadeService.update(especialidade));
    } else {
      this.subscribeToSaveResponse(this.especialidadeService.create(especialidade));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEspecialidade>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(especialidade: IEspecialidade): void {
    this.especialidade = especialidade;
    this.especialidadeFormService.resetForm(this.editForm, especialidade);

    this.profissionalsSharedCollection = this.profissionalService.addProfissionalToCollectionIfMissing<IProfissional>(
      this.profissionalsSharedCollection,
      ...(especialidade.profissionais ?? []),
    );
  }

  protected loadRelationshipsOptions(): void {
    this.profissionalService
      .query()
      .pipe(map((res: HttpResponse<IProfissional[]>) => res.body ?? []))
      .pipe(
        map((profissionals: IProfissional[]) =>
          this.profissionalService.addProfissionalToCollectionIfMissing<IProfissional>(
            profissionals,
            ...(this.especialidade?.profissionais ?? []),
          ),
        ),
      )
      .subscribe((profissionals: IProfissional[]) => (this.profissionalsSharedCollection = profissionals));
  }
}
