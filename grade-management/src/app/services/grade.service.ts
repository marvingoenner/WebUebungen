import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GradeService {
  private apiUrl = 'http://localhost:8080/api/grades'; // API-URL

  constructor(private http: HttpClient) {}

  getGrades(): Observable<any> {
    return this.http.get(this.apiUrl);
  }

  addGrade(subject: string, grade: number): Observable<any> {
    return this.http.post(this.apiUrl, { subject, grade });
  }

  deleteGrade(subject: string): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${subject}`);
  }
}

