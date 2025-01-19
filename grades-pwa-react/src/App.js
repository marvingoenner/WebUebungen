import React, { useState } from "react";
import GradeList from "./components/GradeList";
import AddGradeForm from "./components/AddGradeForm";

function App() {
  const [grades, setGrades] = useState([]);

  const handleAddGrade = (newGrade) => {
    setGrades([...grades, newGrade]);
  };

  const handleDeleteGrade = (index) => {
    const updatedGrades = grades.filter((_, i) => i !== index);
    setGrades(updatedGrades);
  };

  return (
    <div>
      <header>
        <h1>Grades PWA</h1>
      </header>
      <main>
        <AddGradeForm onAdd={handleAddGrade} />
        <GradeList grades={grades} onDelete={handleDeleteGrade} />
      </main>
    </div>
  );
}

export default App;


