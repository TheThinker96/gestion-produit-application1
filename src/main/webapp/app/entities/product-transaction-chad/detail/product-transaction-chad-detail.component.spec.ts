import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProductTransactionChadDetailComponent } from './product-transaction-chad-detail.component';

describe('ProductTransactionChad Management Detail Component', () => {
  let comp: ProductTransactionChadDetailComponent;
  let fixture: ComponentFixture<ProductTransactionChadDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProductTransactionChadDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ productTransaction: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ProductTransactionChadDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ProductTransactionChadDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load productTransaction on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.productTransaction).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
