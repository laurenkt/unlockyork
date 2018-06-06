export default {
    "Introduction": require('../documents/introduction.md'),
    "Team Members": require('../documents/team-members.md'),
    "Quality Assurance": {children: [
            {name:'Document Template', type:'pdf', path:'documents/QA/documenttemplate.pdf'},
            {name:'Content Guidelines', type:'pdf', path:'documents/QA/informationguidelines.pdf'},
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
            {name:'Functional Specification', children:[
                    {name:'FS/5.0',type:'pdf',path:'documents/agile/spec/Functionalspecificationversion5.pdf'},
                    {name:'FS/4.0',type:'pdf',path:'documents/agile/spec/Functionalspecificationversion4.pdf'},
                    {name:'FS/3.0',type:'pdf',path:'documents/agile/spec/Functionalspecificationversion3.pdf'},
                    {name:'FS/2.0',type:'pdf',path:'documents/agile/spec/Functionalspecificationversion2.pdf'},
                    {name:'FS/1.0',type:'pdf',path:'documents/agile/spec/Functionalspecificationversion1.pdf'},
                ]},
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
    "Code Standard": {type:'pdf', path:'documents/code-standard/CodingStandards.pdf'},
    "User Manual": {type:'pdf', path:'documents/user-manual/manual.pdf'},
    "Minutes": "Test",
    "Time-sheets": "Test",
    "Presentations": {children: [
            {name: 'Tender Presentation', type:'pdf', path:'documents/presentation/tender.pdf'},
            {name: 'Sales Presentation', type:'pdf', path:'documents/presentation/salespresentation.pdf'}
        ]},
    "Finance": [
        {
            name: 'Briefing',
            children: [
                {name:'Covering Note', type:'pdf', path:'documents/finance/briefing/covering-note.pdf'},
                {name:'Briefing', type:'pdf', external:'documents/finance/briefing/briefing.xslx', path:'documents/finance/briefing/briefing.pdf'},
            ]
        },
        {
            name: 'Report 1',
            children: [
                {name:'Covering Note', type:'pdf', path:'documents/finance/report-1/cover-sheet.pdf'},
                {name:'Report 1', type:'pdf', external:'documents/finance/report-1/report-1.xslx', path:'documents/finance/report-1/report-1.pdf'},
            ]
        },
        {
            name: 'Report 2',
            children: [
                {name:'Covering Note', type:'pdf', path:'documents/finance/report-2/cover-sheet.pdf'},
                {name:'Report 2', type:'pdf', external:'documents/finance/report-2/report-2.xslx', path:'documents/finance/report-2/report-2.pdf'},
            ]
        },
        {
            name: 'Report 3',
            children: [
                {name:'Covering Note', type:'pdf', path:'documents/finance/report-3/cover-sheet.pdf'},
                {name:'Report 3', type:'pdf', external:'documents/finance/report-3/report-3.xslx', path:'documents/finance/report-3/report-3.pdf'},
            ]
        },
        {
            name: 'Summary',
            children: [
                {name: '3.0', children: [
                        {name: 'Cash Flow', type:'pdf', path:'documents/finance/summary/3.0/cash-flow-3.0.pdf'},
                        {name: 'Summary Report', type:'pdf', path:'documents/finance/summary/3.0/summary-3.0.pdf'},
                        {name: 'Sales Projections', type:'pdf', path:'documents/finance/summary/3.0/sales-projections.pdf'},
                    ]},
                {name: '2.0', children: [
                        {name: 'Cash Flow', type:'pdf', path:'documents/finance/summary/2.0/cash-flow-2.0.pdf'},
                        {name: 'Summary Report', type:'pdf', path:'documents/finance/summary/2.0/summary-2.0.pdf'},
                    ]},
                {name: '1.0', children: [
                        {name: 'Cash Flow', type:'pdf', path:'documents/finance/summary/1.0/cash-flow-1.0.pdf'},
                        {name: 'Summary Report', type:'pdf', path:'documents/finance/summary/1.0/summary-report-1.0.pdf'},
                    ]},
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
    "Contracts": {content: "Test", children: [
        {name: "The Legba Company", type: 'pdf', path:'documents/contracts/g1.pdf'},
        {name: "York Software Development LTD", children: [
            {name:'Contract', type: 'pdf', path:'documents/contracts/g2.pdf'},
                {name:'Client Requirements', type: 'pdf', path:'documents/contracts/clientrequirmentsspecs.pdf'},
                {name:'Development Plan', type: 'pdf', path:'documents/contracts/developmentplan.pdf'},
            ]},
        {name: "SImpLe", type: 'pdf', path:'documents/contracts/g5.pdf'},
    ]},
    "Project-Wide Standards": {name: 'PWS', type:'iframe', path:'documents/pws/index.html'},
    "Marketing": {children: [
        {name:'Marketing Report', type:'pdf', path:'/documents/Marketing/marketresearchandmarketingreport.pdf'},
        {name:'Video', type:'mp4', path:'/documents/videos/marketing.mp4'},
    ]},
    "Code": "Test",
    "Individual Reports": "Test",
    "References": "Test"
}