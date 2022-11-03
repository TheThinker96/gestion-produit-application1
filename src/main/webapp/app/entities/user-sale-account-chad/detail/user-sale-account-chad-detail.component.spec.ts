import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { UserSaleAccountChadDetailComponent } from './user-sale-account-chad-detail.component';

describe('UserSaleAccountChad Management Detail Component', () => {
  let comp: UserSaleAccountChadDetailComponent;
  let fixture: ComponentFixture<UserSaleAccountChadDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UserSaleAccountChadDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ userSaleAccount: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(UserSaleAccountChadDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(UserSaleAccountChadDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load userSaleAccount on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.userSaleAccount).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
