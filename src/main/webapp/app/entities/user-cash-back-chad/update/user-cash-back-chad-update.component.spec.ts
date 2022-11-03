import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { UserCashBackChadFormService } from './user-cash-back-chad-form.service';
import { UserCashBackChadService } from '../service/user-cash-back-chad.service';
import { IUserCashBackChad } from '../user-cash-back-chad.model';

import { UserCashBackChadUpdateComponent } from './user-cash-back-chad-update.component';

describe('UserCashBackChad Management Update Component', () => {
  let comp: UserCashBackChadUpdateComponent;
  let fixture: ComponentFixture<UserCashBackChadUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let userCashBackFormService: UserCashBackChadFormService;
  let userCashBackService: UserCashBackChadService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [UserCashBackChadUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(UserCashBackChadUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(UserCashBackChadUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    userCashBackFormService = TestBed.inject(UserCashBackChadFormService);
    userCashBackService = TestBed.inject(UserCashBackChadService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const userCashBack: IUserCashBackChad = { id: 456 };

      activatedRoute.data = of({ userCashBack });
      comp.ngOnInit();

      expect(comp.userCashBack).toEqual(userCashBack);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUserCashBackChad>>();
      const userCashBack = { id: 123 };
      jest.spyOn(userCashBackFormService, 'getUserCashBackChad').mockReturnValue(userCashBack);
      jest.spyOn(userCashBackService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ userCashBack });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: userCashBack }));
      saveSubject.complete();

      // THEN
      expect(userCashBackFormService.getUserCashBackChad).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(userCashBackService.update).toHaveBeenCalledWith(expect.objectContaining(userCashBack));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUserCashBackChad>>();
      const userCashBack = { id: 123 };
      jest.spyOn(userCashBackFormService, 'getUserCashBackChad').mockReturnValue({ id: null });
      jest.spyOn(userCashBackService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ userCashBack: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: userCashBack }));
      saveSubject.complete();

      // THEN
      expect(userCashBackFormService.getUserCashBackChad).toHaveBeenCalled();
      expect(userCashBackService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUserCashBackChad>>();
      const userCashBack = { id: 123 };
      jest.spyOn(userCashBackService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ userCashBack });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(userCashBackService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
