export default {
  "Introduction": require('../documents/introduction.md'),
  "Team Members": "Team membs",
  "Functional Specification": "fs",
  "QA Manual": "jon",
  "Testing and Integration Plan": "tests",
  "Videos": "video",
  "Design Documentation": "Test",
  "Minutes": "Test",
  "Time-sheets": "Test",
  "Finance Reports and Summary": "Test",
  "Project Management": [
      {name: 'GANTT', children: require('../documents/project-management/gantt/gantt.md')},
      {name: 'PERT', children: [

      ]},
      {name: 'WBS', children: [

      ]},
  ],
  "Contracts": [
      {name: "Group 1", type: 'pdf', path:'documents/contracts/g1.pdf'},
      {name: "Group 2", type: 'pdf', path:'documents/contracts/g2.pdf'},
      {name: "Group 5", type: 'pdf', path:'documents/contracts/g5.pdf'},
  ],
  "Project-Wide Standards": "Test",
  "Market Research": "Test",
  "Code": "Test",
  "Presentation": "Test",
  "Individual Reports": "Test",
  "References": "Test"
}