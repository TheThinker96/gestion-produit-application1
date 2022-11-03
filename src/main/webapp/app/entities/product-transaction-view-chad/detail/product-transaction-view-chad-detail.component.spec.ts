import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProductTransactionViewChadDetailComponent } from './product-transaction-view-chad-detail.component';

describe('ProductTransactionViewChad Management Detail Component', () => {
  let comp: ProductTransactionViewChadDetailComponent;
  let fixture: ComponentFixture<ProductTransactionViewChadDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProductTransactionViewChadDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ productTransactionView: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ProductTransactionViewChadDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ProductTransactionViewChadDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load productTransactionView on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.productTransactionView).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
