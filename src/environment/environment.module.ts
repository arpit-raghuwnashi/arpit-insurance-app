import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';



@NgModule({
  declarations: [],
  imports: [
    CommonModule
  ]
})
export class EnvironmentModule {}

export const environment = {
  production: true,
  projecturl :"http://localhost:8081",
};
