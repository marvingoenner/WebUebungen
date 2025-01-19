import React from "react";

function GradeList({ grades, onDelete }) {
  return (
    <div>
      <h2>Notenübersicht</h2>
      <ul>
        {grades.map((grade, index) => (
          <li key={index}>
            {grade.subject}: {grade.grade}{" "}
            <button onClick={() => onDelete(index)}>Löschen</button>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default GradeList;

