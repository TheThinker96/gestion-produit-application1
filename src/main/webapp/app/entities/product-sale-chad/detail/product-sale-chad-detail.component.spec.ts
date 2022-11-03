import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProductSaleChadDetailComponent } from './product-sale-chad-detail.component';

describe('ProductSaleChad Management Detail Component', () => {
  let comp: ProductSaleChadDetailComponent;
  let fixture: ComponentFixture<ProductSaleChadDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProductSaleChadDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ productSale: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ProductSaleChadDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ProductSaleChadDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load productSale on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.productSale).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
