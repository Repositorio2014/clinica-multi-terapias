import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IEspecialidade } from 'app/entities/especialidade/especialidade.model';
import { EspecialidadeService } from 'app/entities/especialidade/service/especialidade.service';
import { IProfissional } from '../profissional.model';
import { ProfissionalService } from '../service/profissional.service';
import { ProfissionalFormGroup, ProfissionalFormService } from './profissional-form.service';

@Component({
  standalone: true,
  selector: 'jhi-profissional-update',
  templateUrl: './profissional-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ProfissionalUpdateComponent implements OnInit {
  isSaving = false;
  profissional: IProfissional | null = null;

  especialidadesSharedCollection: IEspecialidade[] = [];

  protected profissionalService = inject(ProfissionalService);
  protected profissionalFormService = inject(ProfissionalFormService);
  protected especialidadeService = inject(EspecialidadeService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ProfissionalFormGroup = this.profissionalFormService.createProfissionalFormGroup();

  compareEspecialidade = (o1: IEspecialidade | null, o2: IEspecialidade | null): boolean =>
    this.especialidadeService.compareEspecialidade(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ profissional }) => {
      this.profissional = profissional;
      if (profissional) {
        this.updateForm(profissional);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const profissional = this.profissionalFormService.getProfissional(this.editForm);
    if (profissional.id !== null) {
      this.subscribeToSaveResponse(this.profissionalService.update(profissional));
    } else {
      this.subscribeToSaveResponse(this.profissionalService.create(profissional));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProfissional>>): void {
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

  protected updateForm(profissional: IProfissional): void {
    this.profissional = profissional;
    this.profissionalFormService.resetForm(this.editForm, profissional);

    this.especialidadesSharedCollection = this.especialidadeService.addEspecialidadeToCollectionIfMissing<IEspecialidade>(
      this.especialidadesSharedCollection,
      ...(profissional.especialidades ?? []),
    );
  }

  protected loadRelationshipsOptions(): void {
    this.especialidadeService
      .query()
      .pipe(map((res: HttpResponse<IEspecialidade[]>) => res.body ?? []))
      .pipe(
        map((especialidades: IEspecialidade[]) =>
          this.especialidadeService.addEspecialidadeToCollectionIfMissing<IEspecialidade>(
            especialidades,
            ...(this.profissional?.especialidades ?? []),
          ),
        ),
      )
      .subscribe((especialidades: IEspecialidade[]) => (this.especialidadesSharedCollection = especialidades));
  }
}
