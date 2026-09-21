import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IPaciente } from 'app/entities/paciente/paciente.model';
import { PacienteService } from 'app/entities/paciente/service/paciente.service';
import { IProfissional } from 'app/entities/profissional/profissional.model';
import { ProfissionalService } from 'app/entities/profissional/service/profissional.service';
import { IAgenda } from 'app/entities/agenda/agenda.model';
import { AgendaService } from 'app/entities/agenda/service/agenda.service';
import { IEspecialidade } from 'app/entities/especialidade/especialidade.model';
import { EspecialidadeService } from 'app/entities/especialidade/service/especialidade.service';
import { TipoProntuario } from 'app/entities/enumerations/tipo-prontuario.model';
import { ProntuarioService } from '../service/prontuario.service';
import { IProntuario } from '../prontuario.model';
import { ProntuarioFormGroup, ProntuarioFormService } from './prontuario-form.service';

@Component({
  standalone: true,
  selector: 'jhi-prontuario-update',
  templateUrl: './prontuario-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ProntuarioUpdateComponent implements OnInit {
  isSaving = false;
  prontuario: IProntuario | null = null;
  tipoProntuarioValues = Object.keys(TipoProntuario);

  pacientesSharedCollection: IPaciente[] = [];
  profissionalsSharedCollection: IProfissional[] = [];
  agendaSharedCollection: IAgenda[] = [];
  especialidadesSharedCollection: IEspecialidade[] = [];

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected prontuarioService = inject(ProntuarioService);
  protected prontuarioFormService = inject(ProntuarioFormService);
  protected pacienteService = inject(PacienteService);
  protected profissionalService = inject(ProfissionalService);
  protected agendaService = inject(AgendaService);
  protected especialidadeService = inject(EspecialidadeService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ProntuarioFormGroup = this.prontuarioFormService.createProntuarioFormGroup();

  comparePaciente = (o1: IPaciente | null, o2: IPaciente | null): boolean => this.pacienteService.comparePaciente(o1, o2);

  compareProfissional = (o1: IProfissional | null, o2: IProfissional | null): boolean =>
    this.profissionalService.compareProfissional(o1, o2);

  compareAgenda = (o1: IAgenda | null, o2: IAgenda | null): boolean => this.agendaService.compareAgenda(o1, o2);

  compareEspecialidade = (o1: IEspecialidade | null, o2: IEspecialidade | null): boolean =>
    this.especialidadeService.compareEspecialidade(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ prontuario }) => {
      this.prontuario = prontuario;
      if (prontuario) {
        this.updateForm(prontuario);
      }

      this.loadRelationshipsOptions();
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(
          new EventWithContent<AlertError>('clinicaMultiTerapiasApp.error', { ...err, key: `error.file.${err.key}` }),
        ),
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const prontuario = this.prontuarioFormService.getProntuario(this.editForm);
    if (prontuario.id !== null) {
      this.subscribeToSaveResponse(this.prontuarioService.update(prontuario));
    } else {
      this.subscribeToSaveResponse(this.prontuarioService.create(prontuario));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProntuario>>): void {
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

  protected updateForm(prontuario: IProntuario): void {
    this.prontuario = prontuario;
    this.prontuarioFormService.resetForm(this.editForm, prontuario);

    this.pacientesSharedCollection = this.pacienteService.addPacienteToCollectionIfMissing<IPaciente>(
      this.pacientesSharedCollection,
      prontuario.paciente,
    );
    this.profissionalsSharedCollection = this.profissionalService.addProfissionalToCollectionIfMissing<IProfissional>(
      this.profissionalsSharedCollection,
      prontuario.profissional,
    );
    this.agendaSharedCollection = this.agendaService.addAgendaToCollectionIfMissing<IAgenda>(
      this.agendaSharedCollection,
      prontuario.agenda,
    );
    this.especialidadesSharedCollection = this.especialidadeService.addEspecialidadeToCollectionIfMissing<IEspecialidade>(
      this.especialidadesSharedCollection,
      prontuario.especialidade,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.pacienteService
      .query()
      .pipe(map((res: HttpResponse<IPaciente[]>) => res.body ?? []))
      .pipe(
        map((pacientes: IPaciente[]) =>
          this.pacienteService.addPacienteToCollectionIfMissing<IPaciente>(pacientes, this.prontuario?.paciente),
        ),
      )
      .subscribe((pacientes: IPaciente[]) => (this.pacientesSharedCollection = pacientes));

    this.profissionalService
      .query()
      .pipe(map((res: HttpResponse<IProfissional[]>) => res.body ?? []))
      .pipe(
        map((profissionals: IProfissional[]) =>
          this.profissionalService.addProfissionalToCollectionIfMissing<IProfissional>(profissionals, this.prontuario?.profissional),
        ),
      )
      .subscribe((profissionals: IProfissional[]) => (this.profissionalsSharedCollection = profissionals));

    this.agendaService
      .query()
      .pipe(map((res: HttpResponse<IAgenda[]>) => res.body ?? []))
      .pipe(map((agenda: IAgenda[]) => this.agendaService.addAgendaToCollectionIfMissing<IAgenda>(agenda, this.prontuario?.agenda)))
      .subscribe((agenda: IAgenda[]) => (this.agendaSharedCollection = agenda));

    this.especialidadeService
      .query()
      .pipe(map((res: HttpResponse<IEspecialidade[]>) => res.body ?? []))
      .pipe(
        map((especialidades: IEspecialidade[]) =>
          this.especialidadeService.addEspecialidadeToCollectionIfMissing<IEspecialidade>(especialidades, this.prontuario?.especialidade),
        ),
      )
      .subscribe((especialidades: IEspecialidade[]) => (this.especialidadesSharedCollection = especialidades));
  }
}
