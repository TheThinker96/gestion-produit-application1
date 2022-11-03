import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProductChadDetailComponent } from './product-chad-detail.component';

describe('ProductChad Management Detail Component', () => {
  let comp: ProductChadDetailComponent;
  let fixture: ComponentFixture<ProductChadDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProductChadDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ product: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ProductChadDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ProductChadDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load product on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.product).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
