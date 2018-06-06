export default {
  "Introduction": require('../documents/introduction.md'),
  "Team Members": require('../documents/team-members.md'),
  "Functional Specification": "fs",
  "QA Manual": "jon",
  "Testing and Integration Plan": "tests",
  "Videos": [
      {name:'Kiosk', type:'mp4', path:'documents/videos/kiosk.mp4'}
  ],
  "User Manual": {type:'pdf', path:'documents/user-manual/manual.pdf'},
  "Design Documentation": "Test",
  "Minutes": "Test",
  "Time-sheets": "Test",
  "Finance": [
      {
          name: 'Briefing',
          children: [
              {name:'Covering Note', type:'pdf', path:'documents/finance/briefing/covering-note.pdf'},
              {name:'Briefing', type:'pdf', path:'documents/finance/briefing/briefing.pdf'},
          ]
      },
      {
          name: 'Report 1',
          children: [
              {name:'Covering Note', type:'pdf', path:'documents/finance/report-1/cover-sheet.pdf'},
              {name:'Report 1', type:'pdf', path:'documents/finance/report-1/report-1.pdf'},
          ]
      },
      {
          name: 'Report 2',
          children: [
              {name:'Covering Note', type:'pdf', path:'documents/finance/report-2/cover-sheet.pdf'},
              {name:'Report 2', type:'pdf', path:'documents/finance/report-2/report-2.pdf'},
          ]
      },
      {
          name: 'Report 3',
          children: [
              {name:'Covering Note', type:'pdf', path:'documents/finance/report-3/cover-sheet.pdf'},
              {name:'Report 3', type:'pdf', path:'documents/finance/report-3/report-3.pdf'},
          ]
      }
  ],
  "Project Management": [
      {name: 'GANTT', content: require('../documents/project-management/gantt/gantt.md')},
      {name: 'PERT', type: 'pdf', path: 'documents/project-management/pert/pert.pdf'},
      {name: 'WBS', children: [
          {name: 'WBS/3', type:'pdf', path:'documents/project-management/wbs/3.pdf'},
          {name: 'WBS/2.1', type:'pdf', path:'documents/project-management/wbs/2.1.pdf'},
          {name: 'WBS/2', type:'pdf', path:'documents/project-management/wbs/2.pdf'},
          {name: 'WBS/1.1', type:'pdf', path:'documents/project-management/wbs/1.1.pdf'},
          {name: 'WBS/1', type:'pdf', path:'documents/project-management/wbs/1.pdf'},
      ]},
  ],
  "Contracts": [
      {name: "The Legba Company", type: 'pdf', path:'documents/contracts/g1.pdf'},
      {name: "York Software Development LTD", type: 'pdf', path:'documents/contracts/g2.pdf'},
      {name: "SImpLe", type: 'pdf', path:'documents/contracts/g5.pdf'},
  ],
  "Project-Wide Standards": {name: 'PWS', type:'iframe', path:'documents/pws/index.html'},
  "Market Research": "Test",
  "Code": "Test",
  "Presentation": "Test",
  "Individual Reports": "Test",
  "References": "Test"
}