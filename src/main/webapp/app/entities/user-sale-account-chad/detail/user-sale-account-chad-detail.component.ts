import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserSaleAccountChad } from '../user-sale-account-chad.model';

@Component({
  selector: 'jhi-user-sale-account-chad-detail',
  templateUrl: './user-sale-account-chad-detail.component.html',
})
export class UserSaleAccountChadDetailComponent implements OnInit {
  userSaleAccount: IUserSaleAccountChad | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userSaleAccount }) => {
      this.userSaleAccount = userSaleAccount;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
