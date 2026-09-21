import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IPaciente } from 'app/entities/paciente/paciente.model';
import { PacienteService } from 'app/entities/paciente/service/paciente.service';
import { IProfissional } from 'app/entities/profissional/profissional.model';
import { ProfissionalService } from 'app/entities/profissional/service/profissional.service';
import { ISala } from 'app/entities/sala/sala.model';
import { SalaService } from 'app/entities/sala/service/sala.service';
import { IEspecialidade } from 'app/entities/especialidade/especialidade.model';
import { EspecialidadeService } from 'app/entities/especialidade/service/especialidade.service';
import { IAgenda } from '../agenda.model';
import { AgendaService } from '../service/agenda.service';
import { AgendaFormService } from './agenda-form.service';

import { AgendaUpdateComponent } from './agenda-update.component';

describe('Agenda Management Update Component', () => {
  let comp: AgendaUpdateComponent;
  let fixture: ComponentFixture<AgendaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let agendaFormService: AgendaFormService;
  let agendaService: AgendaService;
  let pacienteService: PacienteService;
  let profissionalService: ProfissionalService;
  let salaService: SalaService;
  let especialidadeService: EspecialidadeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AgendaUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(AgendaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AgendaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    agendaFormService = TestBed.inject(AgendaFormService);
    agendaService = TestBed.inject(AgendaService);
    pacienteService = TestBed.inject(PacienteService);
    profissionalService = TestBed.inject(ProfissionalService);
    salaService = TestBed.inject(SalaService);
    especialidadeService = TestBed.inject(EspecialidadeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Paciente query and add missing value', () => {
      const agenda: IAgenda = { id: 456 };
      const paciente: IPaciente = { id: 25436 };
      agenda.paciente = paciente;

      const pacienteCollection: IPaciente[] = [{ id: 24801 }];
      jest.spyOn(pacienteService, 'query').mockReturnValue(of(new HttpResponse({ body: pacienteCollection })));
      const additionalPacientes = [paciente];
      const expectedCollection: IPaciente[] = [...additionalPacientes, ...pacienteCollection];
      jest.spyOn(pacienteService, 'addPacienteToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ agenda });
      comp.ngOnInit();

      expect(pacienteService.query).toHaveBeenCalled();
      expect(pacienteService.addPacienteToCollectionIfMissing).toHaveBeenCalledWith(
        pacienteCollection,
        ...additionalPacientes.map(expect.objectContaining),
      );
      expect(comp.pacientesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Profissional query and add missing value', () => {
      const agenda: IAgenda = { id: 456 };
      const profissional: IProfissional = { id: 443 };
      agenda.profissional = profissional;

      const profissionalCollection: IProfissional[] = [{ id: 520 }];
      jest.spyOn(profissionalService, 'query').mockReturnValue(of(new HttpResponse({ body: profissionalCollection })));
      const additionalProfissionals = [profissional];
      const expectedCollection: IProfissional[] = [...additionalProfissionals, ...profissionalCollection];
      jest.spyOn(profissionalService, 'addProfissionalToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ agenda });
      comp.ngOnInit();

      expect(profissionalService.query).toHaveBeenCalled();
      expect(profissionalService.addProfissionalToCollectionIfMissing).toHaveBeenCalledWith(
        profissionalCollection,
        ...additionalProfissionals.map(expect.objectContaining),
      );
      expect(comp.profissionalsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Sala query and add missing value', () => {
      const agenda: IAgenda = { id: 456 };
      const sala: ISala = { id: 4708 };
      agenda.sala = sala;

      const salaCollection: ISala[] = [{ id: 25348 }];
      jest.spyOn(salaService, 'query').mockReturnValue(of(new HttpResponse({ body: salaCollection })));
      const additionalSalas = [sala];
      const expectedCollection: ISala[] = [...additionalSalas, ...salaCollection];
      jest.spyOn(salaService, 'addSalaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ agenda });
      comp.ngOnInit();

      expect(salaService.query).toHaveBeenCalled();
      expect(salaService.addSalaToCollectionIfMissing).toHaveBeenCalledWith(
        salaCollection,
        ...additionalSalas.map(expect.objectContaining),
      );
      expect(comp.salasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Especialidade query and add missing value', () => {
      const agenda: IAgenda = { id: 456 };
      const especialidade: IEspecialidade = { id: 30395 };
      agenda.especialidade = especialidade;

      const especialidadeCollection: IEspecialidade[] = [{ id: 30559 }];
      jest.spyOn(especialidadeService, 'query').mockReturnValue(of(new HttpResponse({ body: especialidadeCollection })));
      const additionalEspecialidades = [especialidade];
      const expectedCollection: IEspecialidade[] = [...additionalEspecialidades, ...especialidadeCollection];
      jest.spyOn(especialidadeService, 'addEspecialidadeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ agenda });
      comp.ngOnInit();

      expect(especialidadeService.query).toHaveBeenCalled();
      expect(especialidadeService.addEspecialidadeToCollectionIfMissing).toHaveBeenCalledWith(
        especialidadeCollection,
        ...additionalEspecialidades.map(expect.objectContaining),
      );
      expect(comp.especialidadesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const agenda: IAgenda = { id: 456 };
      const paciente: IPaciente = { id: 24222 };
      agenda.paciente = paciente;
      const profissional: IProfissional = { id: 1432 };
      agenda.profissional = profissional;
      const sala: ISala = { id: 5487 };
      agenda.sala = sala;
      const especialidade: IEspecialidade = { id: 4556 };
      agenda.especialidade = especialidade;

      activatedRoute.data = of({ agenda });
      comp.ngOnInit();

      expect(comp.pacientesSharedCollection).toContain(paciente);
      expect(comp.profissionalsSharedCollection).toContain(profissional);
      expect(comp.salasSharedCollection).toContain(sala);
      expect(comp.especialidadesSharedCollection).toContain(especialidade);
      expect(comp.agenda).toEqual(agenda);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAgenda>>();
      const agenda = { id: 123 };
      jest.spyOn(agendaFormService, 'getAgenda').mockReturnValue(agenda);
      jest.spyOn(agendaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ agenda });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: agenda }));
      saveSubject.complete();

      // THEN
      expect(agendaFormService.getAgenda).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(agendaService.update).toHaveBeenCalledWith(expect.objectContaining(agenda));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAgenda>>();
      const agenda = { id: 123 };
      jest.spyOn(agendaFormService, 'getAgenda').mockReturnValue({ id: null });
      jest.spyOn(agendaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ agenda: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: agenda }));
      saveSubject.complete();

      // THEN
      expect(agendaFormService.getAgenda).toHaveBeenCalled();
      expect(agendaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAgenda>>();
      const agenda = { id: 123 };
      jest.spyOn(agendaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ agenda });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(agendaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('comparePaciente', () => {
      it('Should forward to pacienteService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(pacienteService, 'comparePaciente');
        comp.comparePaciente(entity, entity2);
        expect(pacienteService.comparePaciente).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareProfissional', () => {
      it('Should forward to profissionalService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(profissionalService, 'compareProfissional');
        comp.compareProfissional(entity, entity2);
        expect(profissionalService.compareProfissional).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareSala', () => {
      it('Should forward to salaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(salaService, 'compareSala');
        comp.compareSala(entity, entity2);
        expect(salaService.compareSala).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareEspecialidade', () => {
      it('Should forward to especialidadeService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(especialidadeService, 'compareEspecialidade');
        comp.compareEspecialidade(entity, entity2);
        expect(especialidadeService.compareEspecialidade).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
