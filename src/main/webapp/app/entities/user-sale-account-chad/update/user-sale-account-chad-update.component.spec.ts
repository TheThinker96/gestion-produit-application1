import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { UserSaleAccountChadFormService } from './user-sale-account-chad-form.service';
import { UserSaleAccountChadService } from '../service/user-sale-account-chad.service';
import { IUserSaleAccountChad } from '../user-sale-account-chad.model';

import { UserSaleAccountChadUpdateComponent } from './user-sale-account-chad-update.component';

describe('UserSaleAccountChad Management Update Component', () => {
  let comp: UserSaleAccountChadUpdateComponent;
  let fixture: ComponentFixture<UserSaleAccountChadUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let userSaleAccountFormService: UserSaleAccountChadFormService;
  let userSaleAccountService: UserSaleAccountChadService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [UserSaleAccountChadUpdateComponent],
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
      .overrideTemplate(UserSaleAccountChadUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(UserSaleAccountChadUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    userSaleAccountFormService = TestBed.inject(UserSaleAccountChadFormService);
    userSaleAccountService = TestBed.inject(UserSaleAccountChadService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const userSaleAccount: IUserSaleAccountChad = { id: 456 };

      activatedRoute.data = of({ userSaleAccount });
      comp.ngOnInit();

      expect(comp.userSaleAccount).toEqual(userSaleAccount);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUserSaleAccountChad>>();
      const userSaleAccount = { id: 123 };
      jest.spyOn(userSaleAccountFormService, 'getUserSaleAccountChad').mockReturnValue(userSaleAccount);
      jest.spyOn(userSaleAccountService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ userSaleAccount });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: userSaleAccount }));
      saveSubject.complete();

      // THEN
      expect(userSaleAccountFormService.getUserSaleAccountChad).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(userSaleAccountService.update).toHaveBeenCalledWith(expect.objectContaining(userSaleAccount));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUserSaleAccountChad>>();
      const userSaleAccount = { id: 123 };
      jest.spyOn(userSaleAccountFormService, 'getUserSaleAccountChad').mockReturnValue({ id: null });
      jest.spyOn(userSaleAccountService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ userSaleAccount: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: userSaleAccount }));
      saveSubject.complete();

      // THEN
      expect(userSaleAccountFormService.getUserSaleAccountChad).toHaveBeenCalled();
      expect(userSaleAccountService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUserSaleAccountChad>>();
      const userSaleAccount = { id: 123 };
      jest.spyOn(userSaleAccountService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ userSaleAccount });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(userSaleAccountService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
