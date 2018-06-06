export default {
    "Introduction": require('../documents/introduction.md'),
    "Team Members": require('../documents/team-members.md'),
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
    "Design Documentation": {children: [
            {name:'User Story Tracker', children: [
                    {name: 'S01', type:'pdf', path:'documents/agile/s01.pdf'},
                    {name: 'S02', type:'pdf', path:'documents/agile/s02.pdf'},
                    {name: 'S03', type:'pdf', path:'documents/agile/s03.pdf'},
                    {name: 'S04', type:'pdf', path:'documents/agile/s04.pdf'},
                    {name: 'S05', type:'pdf', path:'documents/agile/s05.pdf'},
                    {name: 'S06', type:'pdf', path:'documents/agile/s06.pdf'},
                    {name: 'S07', type:'pdf', path:'documents/agile/s07.pdf'},
                    {name: 'S13', type:'pdf', path:'documents/agile/s13.pdf'},
                ]}
        ]},
    "Testing and Integration": "tests",
    "Videos": [
        {name:'Kiosk', type:'mp4', path:'documents/videos/kiosk.mp4'},
        {name:'Android', type:'mp4', path:'documents/videos/finalphonemovie.mp4'},
    ],
    "User Manual": {type:'pdf', path:'documents/user-manual/manual.pdf'},
    "Minutes": "Test",
    "Time-sheets": "Test",
    "Presentations": {children: [
            {name: 'Sales Presentation', type:'pdf', path:'documents/presentation/salespresentation.pdf'}
        ]},
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