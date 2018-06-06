export default {
  "Introduction": require('../documents/introduction.md'),
  "Team Members": require('../documents/team-members.md'),
  "Functional Specification": "fs",
  "Quality Assurance": {children: [
          {name: 'Document Template', type:'pdf', path:'documents/QA/documenttemplate.pdf'},
          {name: 'Content Guidelines', type:'pdf', path:'documents/QA/informationguidelines.pdf'},
          {name:'QA Metrics Reports', children:[
                  {name:'Iteration 1', type:'pdf', path:'documents/QA/QAMetricreport-iterationone.pdf'},
                  {name:'Iteration 2', type:'pdf', path:'documents/QA/qametricreport-iterationtwo.pdf'},
              ]},
          {name:'End of Iteration Meetings', children:[
                  {name:'Iteration 1', type:'pdf', path:'documents/QA/Endofiteration1meeting.pdf'},
                  {name:'Iteration 2', type:'pdf', path:'documents/QA/Endofiteration2meeting.pdf'},
              ]},
          {name:'QA Manual', children: [
                  {name:'QAM/2.2', type:'pdf', path:'documents/QA/QAM_version2.2.pdf'},
                  {name:'QAM/2.1', type:'pdf', path:'documents/QA/QAM_version2.1.pdf'},
                  {name:'QAM/2.0', type:'pdf', path:'documents/QA/QAM_version2.pdf'},
                  {name:'QAM/1.0', type:'pdf', path:'documents/QA/QAM_verison1.0.pdf'},
              ]}
      ]},
  "Design Documentation": "Test",
  "Testing and Integration": "tests",
  "Videos": [
      {name:'Kiosk', type:'mp4', path:'documents/videos/kiosk.mp4'}
  ],
  "User Manual": {type:'pdf', path:'documents/user-manual/manual.pdf'},
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