import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { UserCashBackChadDetailComponent } from './user-cash-back-chad-detail.component';

describe('UserCashBackChad Management Detail Component', () => {
  let comp: UserCashBackChadDetailComponent;
  let fixture: ComponentFixture<UserCashBackChadDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UserCashBackChadDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ userCashBack: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(UserCashBackChadDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(UserCashBackChadDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load userCashBack on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.userCashBack).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
