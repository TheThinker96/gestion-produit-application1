jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { UserCashBackChadService } from '../service/user-cash-back-chad.service';

import { UserCashBackChadDeleteDialogComponent } from './user-cash-back-chad-delete-dialog.component';

describe('UserCashBackChad Management Delete Component', () => {
  let comp: UserCashBackChadDeleteDialogComponent;
  let fixture: ComponentFixture<UserCashBackChadDeleteDialogComponent>;
  let service: UserCashBackChadService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [UserCashBackChadDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(UserCashBackChadDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(UserCashBackChadDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(UserCashBackChadService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete(123);
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith(123);
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      })
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
