import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GradeOverviewComponent } from './grade-overview.component';

describe('GradeOverviewComponent', () => {
  let component: GradeOverviewComponent;
  let fixture: ComponentFixture<GradeOverviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GradeOverviewComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GradeOverviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
