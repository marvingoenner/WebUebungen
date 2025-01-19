import React, { useState } from "react";

function AddGradeForm({ onAdd }) {
  const [subject, setSubject] = useState("");
  const [grade, setGrade] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!subject || !grade) return;
    onAdd({ subject, grade });
    setSubject("");
    setGrade("");
  };

  return (
    <form onSubmit={handleSubmit}>
      <h2>Neue Note hinzufügen</h2>
      <div>
        <label>
          Fach:
          <input
            type="text"
            value={subject}
            onChange={(e) => setSubject(e.target.value)}
          />
        </label>
      </div>
      <div>
        <label>
          Note:
          <input
            type="number"
            value={grade}
            onChange={(e) => setGrade(e.target.value)}
          />
        </label>
      </div>
      <button type="submit">Hinzufügen</button>
    </form>
  );
}

export default AddGradeForm;

