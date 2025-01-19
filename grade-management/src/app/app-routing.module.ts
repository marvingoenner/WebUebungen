import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { GradeOverviewComponent } from './components/grade-overview/grade-overview.component';

const routes: Routes = [
  { path: 'grades', component: GradeOverviewComponent },
  { path: '', redirectTo: '/grades', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

