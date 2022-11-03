import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserCashBackChad } from '../user-cash-back-chad.model';

@Component({
  selector: 'jhi-user-cash-back-chad-detail',
  templateUrl: './user-cash-back-chad-detail.component.html',
})
export class UserCashBackChadDetailComponent implements OnInit {
  userCashBack: IUserCashBackChad | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userCashBack }) => {
      this.userCashBack = userCashBack;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
