export default {
  "Introduction": require('../documents/introduction.md'),
  "Team Members": require('../documents/team-members.md'),
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
      {name: 'PERT', type: 'pdf', path: 'documents/project-management/pert/pert.pdf'},
      {name: 'WBS', children: [
              {name: 'WBS/1', type:'pdf', path:'documents/project-management/wbs/1.pdf'},
              {name: 'WBS/1.1', type:'pdf', path:'documents/project-management/wbs/1.1.pdf'},
              {name: 'WBS/2', type:'pdf', path:'documents/project-management/wbs/2.pdf'},
              {name: 'WBS/2.1', type:'pdf', path:'documents/project-management/wbs/2.1.pdf'},
              {name: 'WBS/3', type:'pdf', path:'documents/project-management/wbs/3.pdf'},
      ]},
  ],
  "Contracts": [
      {name: "Group 1", type: 'pdf', path:'documents/contracts/g1.pdf'},
      {name: "Group 2", type: 'pdf', path:'documents/contracts/g2.pdf'},
      {name: "Group 5", type: 'pdf', path:'documents/contracts/g5.pdf'},
  ],
  "Project-Wide Standards": {name: 'PWS', type:'iframe', path:'documents/pws/index.html'},
  "Market Research": "Test",
  "Code": "Test",
  "Presentation": "Test",
  "Individual Reports": "Test",
  "References": "Test"
}