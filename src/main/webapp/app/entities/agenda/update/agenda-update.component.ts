import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IPaciente } from 'app/entities/paciente/paciente.model';
import { PacienteService } from 'app/entities/paciente/service/paciente.service';
import { IProfissional } from 'app/entities/profissional/profissional.model';
import { ProfissionalService } from 'app/entities/profissional/service/profissional.service';
import { ISala } from 'app/entities/sala/sala.model';
import { SalaService } from 'app/entities/sala/service/sala.service';
import { IEspecialidade } from 'app/entities/especialidade/especialidade.model';
import { EspecialidadeService } from 'app/entities/especialidade/service/especialidade.service';
import { StatusAgendamento } from 'app/entities/enumerations/status-agendamento.model';
import { AgendaService } from '../service/agenda.service';
import { IAgenda } from '../agenda.model';
import { AgendaFormGroup, AgendaFormService } from './agenda-form.service';

@Component({
  standalone: true,
  selector: 'jhi-agenda-update',
  templateUrl: './agenda-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AgendaUpdateComponent implements OnInit {
  isSaving = false;
  agenda: IAgenda | null = null;
  statusAgendamentoValues = Object.keys(StatusAgendamento);

  pacientesSharedCollection: IPaciente[] = [];
  profissionalsSharedCollection: IProfissional[] = [];
  salasSharedCollection: ISala[] = [];
  especialidadesSharedCollection: IEspecialidade[] = [];

  protected agendaService = inject(AgendaService);
  protected agendaFormService = inject(AgendaFormService);
  protected pacienteService = inject(PacienteService);
  protected profissionalService = inject(ProfissionalService);
  protected salaService = inject(SalaService);
  protected especialidadeService = inject(EspecialidadeService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AgendaFormGroup = this.agendaFormService.createAgendaFormGroup();

  comparePaciente = (o1: IPaciente | null, o2: IPaciente | null): boolean => this.pacienteService.comparePaciente(o1, o2);

  compareProfissional = (o1: IProfissional | null, o2: IProfissional | null): boolean =>
    this.profissionalService.compareProfissional(o1, o2);

  compareSala = (o1: ISala | null, o2: ISala | null): boolean => this.salaService.compareSala(o1, o2);

  compareEspecialidade = (o1: IEspecialidade | null, o2: IEspecialidade | null): boolean =>
    this.especialidadeService.compareEspecialidade(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ agenda }) => {
      this.agenda = agenda;
      if (agenda) {
        this.updateForm(agenda);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const agenda = this.agendaFormService.getAgenda(this.editForm);
    if (agenda.id !== null) {
      this.subscribeToSaveResponse(this.agendaService.update(agenda));
    } else {
      this.subscribeToSaveResponse(this.agendaService.create(agenda));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAgenda>>): void {
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

  protected updateForm(agenda: IAgenda): void {
    this.agenda = agenda;
    this.agendaFormService.resetForm(this.editForm, agenda);

    this.pacientesSharedCollection = this.pacienteService.addPacienteToCollectionIfMissing<IPaciente>(
      this.pacientesSharedCollection,
      agenda.paciente,
    );
    this.profissionalsSharedCollection = this.profissionalService.addProfissionalToCollectionIfMissing<IProfissional>(
      this.profissionalsSharedCollection,
      agenda.profissional,
    );
    this.salasSharedCollection = this.salaService.addSalaToCollectionIfMissing<ISala>(this.salasSharedCollection, agenda.sala);
    this.especialidadesSharedCollection = this.especialidadeService.addEspecialidadeToCollectionIfMissing<IEspecialidade>(
      this.especialidadesSharedCollection,
      agenda.especialidade,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.pacienteService
      .query()
      .pipe(map((res: HttpResponse<IPaciente[]>) => res.body ?? []))
      .pipe(
        map((pacientes: IPaciente[]) => this.pacienteService.addPacienteToCollectionIfMissing<IPaciente>(pacientes, this.agenda?.paciente)),
      )
      .subscribe((pacientes: IPaciente[]) => (this.pacientesSharedCollection = pacientes));

    this.profissionalService
      .query()
      .pipe(map((res: HttpResponse<IProfissional[]>) => res.body ?? []))
      .pipe(
        map((profissionals: IProfissional[]) =>
          this.profissionalService.addProfissionalToCollectionIfMissing<IProfissional>(profissionals, this.agenda?.profissional),
        ),
      )
      .subscribe((profissionals: IProfissional[]) => (this.profissionalsSharedCollection = profissionals));

    this.salaService
      .query()
      .pipe(map((res: HttpResponse<ISala[]>) => res.body ?? []))
      .pipe(map((salas: ISala[]) => this.salaService.addSalaToCollectionIfMissing<ISala>(salas, this.agenda?.sala)))
      .subscribe((salas: ISala[]) => (this.salasSharedCollection = salas));

    this.especialidadeService
      .query()
      .pipe(map((res: HttpResponse<IEspecialidade[]>) => res.body ?? []))
      .pipe(
        map((especialidades: IEspecialidade[]) =>
          this.especialidadeService.addEspecialidadeToCollectionIfMissing<IEspecialidade>(especialidades, this.agenda?.especialidade),
        ),
      )
      .subscribe((especialidades: IEspecialidade[]) => (this.especialidadesSharedCollection = especialidades));
  }
}
