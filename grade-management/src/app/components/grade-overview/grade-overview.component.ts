import { Component, OnInit } from '@angular/core';
import { GradeService } from '../../services/grade.service';

@Component({
  selector: 'app-grade-overview',
  templateUrl: './grade-overview.component.html',
  styleUrls: ['./grade-overview.component.css']
})
export class GradeOverviewComponent implements OnInit {
  grades: any[] = [];

  constructor(private gradeService: GradeService) {}

  ngOnInit(): void {
    this.gradeService.getGrades().subscribe(data => {
      this.grades = data;
    });
  }
}
