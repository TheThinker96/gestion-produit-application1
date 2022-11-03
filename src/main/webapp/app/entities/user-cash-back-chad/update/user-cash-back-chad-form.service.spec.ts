import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../user-cash-back-chad.test-samples';

import { UserCashBackChadFormService } from './user-cash-back-chad-form.service';

describe('UserCashBackChad Form Service', () => {
  let service: UserCashBackChadFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UserCashBackChadFormService);
  });

  describe('Service methods', () => {
    describe('createUserCashBackChadFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createUserCashBackChadFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            montant: expect.any(Object),
            balance: expect.any(Object),
            statut: expect.any(Object),
          })
        );
      });

      it('passing IUserCashBackChad should create a new form with FormGroup', () => {
        const formGroup = service.createUserCashBackChadFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            montant: expect.any(Object),
            balance: expect.any(Object),
            statut: expect.any(Object),
          })
        );
      });
    });

    describe('getUserCashBackChad', () => {
      it('should return NewUserCashBackChad for default UserCashBackChad initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createUserCashBackChadFormGroup(sampleWithNewData);

        const userCashBack = service.getUserCashBackChad(formGroup) as any;

        expect(userCashBack).toMatchObject(sampleWithNewData);
      });

      it('should return NewUserCashBackChad for empty UserCashBackChad initial value', () => {
        const formGroup = service.createUserCashBackChadFormGroup();

        const userCashBack = service.getUserCashBackChad(formGroup) as any;

        expect(userCashBack).toMatchObject({});
      });

      it('should return IUserCashBackChad', () => {
        const formGroup = service.createUserCashBackChadFormGroup(sampleWithRequiredData);

        const userCashBack = service.getUserCashBackChad(formGroup) as any;

        expect(userCashBack).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IUserCashBackChad should not enable id FormControl', () => {
        const formGroup = service.createUserCashBackChadFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewUserCashBackChad should disable id FormControl', () => {
        const formGroup = service.createUserCashBackChadFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
