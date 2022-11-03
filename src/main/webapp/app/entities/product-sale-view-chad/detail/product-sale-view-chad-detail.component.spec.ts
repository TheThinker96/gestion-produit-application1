import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProductSaleViewChadDetailComponent } from './product-sale-view-chad-detail.component';

describe('ProductSaleViewChad Management Detail Component', () => {
  let comp: ProductSaleViewChadDetailComponent;
  let fixture: ComponentFixture<ProductSaleViewChadDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProductSaleViewChadDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ productSaleView: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ProductSaleViewChadDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ProductSaleViewChadDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load productSaleView on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.productSaleView).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
