import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IPaciente } from 'app/entities/paciente/paciente.model';
import { PacienteService } from 'app/entities/paciente/service/paciente.service';
import { IProfissional } from 'app/entities/profissional/profissional.model';
import { ProfissionalService } from 'app/entities/profissional/service/profissional.service';
import { IAgenda } from 'app/entities/agenda/agenda.model';
import { AgendaService } from 'app/entities/agenda/service/agenda.service';
import { IEspecialidade } from 'app/entities/especialidade/especialidade.model';
import { EspecialidadeService } from 'app/entities/especialidade/service/especialidade.service';
import { IProntuario } from '../prontuario.model';
import { ProntuarioService } from '../service/prontuario.service';
import { ProntuarioFormService } from './prontuario-form.service';

import { ProntuarioUpdateComponent } from './prontuario-update.component';

describe('Prontuario Management Update Component', () => {
  let comp: ProntuarioUpdateComponent;
  let fixture: ComponentFixture<ProntuarioUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let prontuarioFormService: ProntuarioFormService;
  let prontuarioService: ProntuarioService;
  let pacienteService: PacienteService;
  let profissionalService: ProfissionalService;
  let agendaService: AgendaService;
  let especialidadeService: EspecialidadeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ProntuarioUpdateComponent],
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
      .overrideTemplate(ProntuarioUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProntuarioUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    prontuarioFormService = TestBed.inject(ProntuarioFormService);
    prontuarioService = TestBed.inject(ProntuarioService);
    pacienteService = TestBed.inject(PacienteService);
    profissionalService = TestBed.inject(ProfissionalService);
    agendaService = TestBed.inject(AgendaService);
    especialidadeService = TestBed.inject(EspecialidadeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Paciente query and add missing value', () => {
      const prontuario: IProntuario = { id: 456 };
      const paciente: IPaciente = { id: 20329 };
      prontuario.paciente = paciente;

      const pacienteCollection: IPaciente[] = [{ id: 17052 }];
      jest.spyOn(pacienteService, 'query').mockReturnValue(of(new HttpResponse({ body: pacienteCollection })));
      const additionalPacientes = [paciente];
      const expectedCollection: IPaciente[] = [...additionalPacientes, ...pacienteCollection];
      jest.spyOn(pacienteService, 'addPacienteToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ prontuario });
      comp.ngOnInit();

      expect(pacienteService.query).toHaveBeenCalled();
      expect(pacienteService.addPacienteToCollectionIfMissing).toHaveBeenCalledWith(
        pacienteCollection,
        ...additionalPacientes.map(expect.objectContaining),
      );
      expect(comp.pacientesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Profissional query and add missing value', () => {
      const prontuario: IProntuario = { id: 456 };
      const profissional: IProfissional = { id: 32005 };
      prontuario.profissional = profissional;

      const profissionalCollection: IProfissional[] = [{ id: 10582 }];
      jest.spyOn(profissionalService, 'query').mockReturnValue(of(new HttpResponse({ body: profissionalCollection })));
      const additionalProfissionals = [profissional];
      const expectedCollection: IProfissional[] = [...additionalProfissionals, ...profissionalCollection];
      jest.spyOn(profissionalService, 'addProfissionalToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ prontuario });
      comp.ngOnInit();

      expect(profissionalService.query).toHaveBeenCalled();
      expect(profissionalService.addProfissionalToCollectionIfMissing).toHaveBeenCalledWith(
        profissionalCollection,
        ...additionalProfissionals.map(expect.objectContaining),
      );
      expect(comp.profissionalsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Agenda query and add missing value', () => {
      const prontuario: IProntuario = { id: 456 };
      const agenda: IAgenda = { id: 23405 };
      prontuario.agenda = agenda;

      const agendaCollection: IAgenda[] = [{ id: 20695 }];
      jest.spyOn(agendaService, 'query').mockReturnValue(of(new HttpResponse({ body: agendaCollection })));
      const additionalAgenda = [agenda];
      const expectedCollection: IAgenda[] = [...additionalAgenda, ...agendaCollection];
      jest.spyOn(agendaService, 'addAgendaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ prontuario });
      comp.ngOnInit();

      expect(agendaService.query).toHaveBeenCalled();
      expect(agendaService.addAgendaToCollectionIfMissing).toHaveBeenCalledWith(
        agendaCollection,
        ...additionalAgenda.map(expect.objectContaining),
      );
      expect(comp.agendaSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Especialidade query and add missing value', () => {
      const prontuario: IProntuario = { id: 456 };
      const especialidade: IEspecialidade = { id: 11010 };
      prontuario.especialidade = especialidade;

      const especialidadeCollection: IEspecialidade[] = [{ id: 4635 }];
      jest.spyOn(especialidadeService, 'query').mockReturnValue(of(new HttpResponse({ body: especialidadeCollection })));
      const additionalEspecialidades = [especialidade];
      const expectedCollection: IEspecialidade[] = [...additionalEspecialidades, ...especialidadeCollection];
      jest.spyOn(especialidadeService, 'addEspecialidadeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ prontuario });
      comp.ngOnInit();

      expect(especialidadeService.query).toHaveBeenCalled();
      expect(especialidadeService.addEspecialidadeToCollectionIfMissing).toHaveBeenCalledWith(
        especialidadeCollection,
        ...additionalEspecialidades.map(expect.objectContaining),
      );
      expect(comp.especialidadesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const prontuario: IProntuario = { id: 456 };
      const paciente: IPaciente = { id: 23956 };
      prontuario.paciente = paciente;
      const profissional: IProfissional = { id: 19510 };
      prontuario.profissional = profissional;
      const agenda: IAgenda = { id: 439 };
      prontuario.agenda = agenda;
      const especialidade: IEspecialidade = { id: 30881 };
      prontuario.especialidade = especialidade;

      activatedRoute.data = of({ prontuario });
      comp.ngOnInit();

      expect(comp.pacientesSharedCollection).toContain(paciente);
      expect(comp.profissionalsSharedCollection).toContain(profissional);
      expect(comp.agendaSharedCollection).toContain(agenda);
      expect(comp.especialidadesSharedCollection).toContain(especialidade);
      expect(comp.prontuario).toEqual(prontuario);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProntuario>>();
      const prontuario = { id: 123 };
      jest.spyOn(prontuarioFormService, 'getProntuario').mockReturnValue(prontuario);
      jest.spyOn(prontuarioService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ prontuario });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: prontuario }));
      saveSubject.complete();

      // THEN
      expect(prontuarioFormService.getProntuario).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(prontuarioService.update).toHaveBeenCalledWith(expect.objectContaining(prontuario));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProntuario>>();
      const prontuario = { id: 123 };
      jest.spyOn(prontuarioFormService, 'getProntuario').mockReturnValue({ id: null });
      jest.spyOn(prontuarioService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ prontuario: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: prontuario }));
      saveSubject.complete();

      // THEN
      expect(prontuarioFormService.getProntuario).toHaveBeenCalled();
      expect(prontuarioService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProntuario>>();
      const prontuario = { id: 123 };
      jest.spyOn(prontuarioService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ prontuario });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(prontuarioService.update).toHaveBeenCalled();
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

    describe('compareAgenda', () => {
      it('Should forward to agendaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(agendaService, 'compareAgenda');
        comp.compareAgenda(entity, entity2);
        expect(agendaService.compareAgenda).toHaveBeenCalledWith(entity, entity2);
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
