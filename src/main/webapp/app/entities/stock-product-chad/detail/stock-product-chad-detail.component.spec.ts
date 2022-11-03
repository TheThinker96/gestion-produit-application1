import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { StockProductChadDetailComponent } from './stock-product-chad-detail.component';

describe('StockProductChad Management Detail Component', () => {
  let comp: StockProductChadDetailComponent;
  let fixture: ComponentFixture<StockProductChadDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [StockProductChadDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ stockProduct: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(StockProductChadDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(StockProductChadDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load stockProduct on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.stockProduct).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
