import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { StockProductViewChadDetailComponent } from './stock-product-view-chad-detail.component';

describe('StockProductViewChad Management Detail Component', () => {
  let comp: StockProductViewChadDetailComponent;
  let fixture: ComponentFixture<StockProductViewChadDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [StockProductViewChadDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ stockProductView: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(StockProductViewChadDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(StockProductViewChadDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load stockProductView on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.stockProductView).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
