export default {
    "Introduction": require('../documents/introduction.md'),
    "Team Members": require('../documents/team-members.md'),
    "Quality Assurance": {children: [
            {name:'Document Template', type:'pdf', path:'documents/QA/documenttemplate.pdf'},
            {name:'Content Guidelines', type:'pdf', content:require('../documents/QA/informationguidelines.md'),
                path:'documents/QA/informationguidelines.pdf'},
            {name:'QA Metrics Reports', content:require('../documents/QA/qametrics.md'),
                children:[
                    {name:'Iteration 1', type:'pdf', path:'documents/QA/QAMetricreport-iterationone.pdf'},
                    {name:'Iteration 2', type:'pdf', path:'documents/QA/qametricreport-iterationtwo.pdf'},
                ]},
            {name:'End of Iteration Meetings', content:require('../documents/QA/endofiteration.md'),
                children:[
                    {name:'Iteration 1', type:'pdf', path:'documents/QA/Endofiteration1meeting.pdf'},
                    {name:'Iteration 2', type:'pdf', path:'documents/QA/Endofiteration2meeting.pdf'},
                ]},
            {name:'QA Manual', content:require('../documents/QA/qamanual.md'),
                children: [
                    {name:'QAM/2.2', type:'pdf', path:'documents/QA/QAM_version2.2.pdf'},
                    {name:'QAM/2.1', type:'pdf', path:'documents/QA/QAM_version2.1.pdf'},
                    {name:'QAM/2.0', type:'pdf', path:'documents/QA/QAM_version2.pdf'},
                    {name:'QAM/1.0', type:'pdf', path:'documents/QA/QAM_verison1.0.pdf'},
                ]}
        ]},
    "Design Documentation": {children: [
            {name:'Functional Specification', content: require('../documents/agile/spec/spec.md'), children:[
                    {name:'FS/5.0',type:'pdf',path:'documents/agile/spec/Functionalspecificationversion5.pdf'},
                    {name:'FS/4.0',type:'pdf',path:'documents/agile/spec/Functionalspecificationversion4.pdf'},
                    {name:'FS/3.0',type:'pdf',path:'documents/agile/spec/Functionalspecificationversion3.pdf'},
                    {name:'FS/2.0',type:'pdf',path:'documents/agile/spec/Functionalspecificationversion2.pdf'},
                    {name:'FS/1.0',type:'pdf',path:'documents/agile/spec/Functionalspecificationversion1.pdf'},
                ]},
            {name:'User Story Tracker',
                content: require('../documents/QA/user-stories.md'),
                children: [
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
    "Testing and Integration": {
        children: [
            {name: 'Test Plan', content: require('../documents/Testing/plan.md'), children:[
                    {name: 'FTIP/2.0', type:'pdf', path:'documents/Testing/test_plan_v2.pdf'},
                    {name: 'FTIP/1.0', type:'pdf', path:'documents/Testing/test_plan_v1.pdf'},
                ]},
            {name: 'Test Summary', type:'pdf', content: require('../documents/Testing/summary.md'),
                path:'documents/Testing/test_summary.pdf'},
            {name: 'Test Reports', content:require('../documents/Testing/reports.md'), children:[
                    {name:'1', type:'pdf', path:'documents/Testing/jack_1st_iteration_tests.pdf'},
                    {name:'2', type:'pdf', path:'documents/Testing/jack_instrumentation_tests.pdf'},
                    {name:'3', type:'pdf', path:'documents/Testing/jack_system_tests.pdf'},
                    {name:'4', type:'pdf', path:'documents/Testing/jack_visual_tests.pdf'},
                    {name:'5', type:'pdf', path:'documents/Testing/james_1st_iteration_tests.pdf'},
                    {name:'6', type:'pdf', path:'documents/Testing/james_instrumentation_tests.pdf'},
                    {name:'7', type:'pdf', path:'documents/Testing/james_system_tests.pdf'},
                    {name:'8', type:'pdf', path:'documents/Testing/james_visual_tests.pdf'},
                    {name:'9', type:'pdf', path:'documents/Testing/liam_1st_iteration_tests.pdf'},
                    {name:'10', type:'pdf', path:'documents/Testing/liam_lauren_jon_test_reports_iteration2.pdf'},
                    {name:'11', type:'pdf', path:'documents/Testing/ollie_1st_iteration_tests.pdf'},
                    {name:'12', type:'pdf', path:'documents/Testing/tom_and_ollie_s03_tests.pdf'},
                    {name:'13', type:'pdf', path:'documents/Testing/tom_kit4.0.pdf'},
                    {name:'14', type:'pdf', path:'documents/Testing/tom_kst2.pdf'},
                    {name:'15', type:'pdf', path:'documents/Testing/tom_kst3.pdf'},
                    {name:'16', type:'pdf', path:'documents/Testing/tom_test_report_s02.pdf'},
                    {name:'17', type:'pdf', path:'documents/Testing/tom_test_report_s07.pdf'},
                ]},
        ]
    },
    "Code Standard": {type:'pdf', path:'documents/code-standard/CodingStandards.pdf'},
    "User Manual": {children: [{
        name:'Manual',
        type:'pdf',
        path:'documents/user-manual/manual.pdf'},
        {name:'Kiosk Video', type:'mp4', path:'documents/videos/kiosk.mp4'},
        {name:'Android Video', type:'mp4', path:'documents/videos/finalphonemovie.mp4'},
    ]},
    "Minutes": {children: [
            {name: '1', type:'pdf', path:'documents/Minutes/Meeting-1.pdf'},
            {name: '2', type:'pdf', path:'documents/Minutes/Meeting-2.pdf'},
            {name: '3', type:'pdf', path:'documents/Minutes/Meeting-3.pdf'},
            {name: '4', type:'pdf', path:'documents/Minutes/Meeting-4.pdf'},
            {name: '5', type:'pdf', path:'documents/Minutes/Meeting-5.pdf'},
            {name: '6', type:'pdf', path:'documents/Minutes/Meeting-6.pdf'},
            {name: '7', type:'pdf', path:'documents/Minutes/Meeting-7.pdf'},
            {name: '8', type:'pdf', path:'documents/Minutes/Meeting-8.pdf'},
            {name: '9', type:'pdf', path:'documents/Minutes/Meeting-9.pdf'},
            {name: '10', type:'pdf', path:'documents/Minutes/Meeting-10.pdf'},
            {name: '11', type:'pdf', path:'documents/Minutes/Meeting-11.pdf'},
            {name: '12', type:'pdf', path:'documents/Minutes/Meeting-12.pdf'},
            {name: '13', type:'pdf', path:'documents/Minutes/Meeting-13.pdf'},
            {name: '14', type:'pdf', path:'documents/Minutes/Meeting-14.pdf'},
        ]},
    "Time-sheets": [
        {name:'Jack', children: [
                {name:'2', type:'pdf', path:'documents/time-sheets/Jack/Week2(Spring)-JackMckeown.pdf'},
                {name:'3', type:'pdf', path:'documents/time-sheets/Jack/Week3(Spring)-JackMckeown.pdf'},
                {name:'4', type:'pdf', path:'documents/time-sheets/Jack/Week4(Spring)-JackMckeown.pdf'},
                {name:'5', type:'pdf', path:'documents/time-sheets/Jack/Week5(Spring)-JackMckeown.pdf'},
                {name:'6', type:'pdf', path:'documents/time-sheets/Jack/Week6(Spring)-JackMckeown.pdf'},
                {name:'7', type:'pdf', path:'documents/time-sheets/Jack/Week7(Spring)-JackMckeown.pdf'},
                {name:'8', type:'pdf', path:'documents/time-sheets/Jack/Week8(Spring)-JackMckeown.pdf'},
                {name:'9', type:'pdf', path:'documents/time-sheets/Jack/Week9(Spring)-JackMckeown.pdf'},
                {name:'10', type:'pdf', path:'documents/time-sheets/Jack/Week10(Spring)-JackMckeown.pdf'},
                {name:'11', type:'pdf', path:'documents/time-sheets/Jack/Week11(Spring)-JackMckeown.pdf'},
                {name:'12', type:'pdf', path:'documents/time-sheets/Jack/Week12(Spring)-JackMckeown.pdf'},
                {name:'13', type:'pdf', path:'documents/time-sheets/Jack/Week13(Spring)-JackMckeown.pdf'},
                {name:'14', type:'pdf', path:'documents/time-sheets/Jack/Week14(Spring)-JackMckeown.pdf'},
                {name:'15', type:'pdf', path:'documents/time-sheets/Jack/Week15(Summerwk1)-JackMckeown.pdf'},
                {name:'16', type:'pdf', path:'documents/time-sheets/Jack/Week16(Summerwk2)-JackMckeown.pdf'},
                {name:'17', type:'pdf', path:'documents/time-sheets/Jack/Week17(Summerwk3)-JackMckeown.pdf'},
                {name:'18', type:'pdf', path:'documents/time-sheets/Jack/Week-18(Summerwk4)-JackMckeown.pdf'},
                {name:'19', type:'pdf', path:'documents/time-sheets/Jack/Week-19(Summerwk5)-JackMckeown.pdf'},
                {name:'20', type:'pdf', path:'documents/time-sheets/Jack/Week-20(Summerwk6)-JackMckeown.pdf'},
                {name:'21', type:'pdf', path:'documents/time-sheets/Jack/Week21(summerwk7)-JackMckeown.pdf'},
                {name:'22', type:'pdf', path:'documents/time-sheets/Jack/Week-22(Summerwk8)-JackMckeown.pdf'},
            ]},
        {name:'Tom', children: [
                {name:'2', type:'pdf', path:'documents/time-sheets/Tom/Week-2-Tom.pdf'},
                {name:'3', type:'pdf', path:'documents/time-sheets/Tom/Week-3-Tom.pdf'},
                {name:'4', type:'pdf', path:'documents/time-sheets/Tom/Week-4-Tom.pdf'},
                {name:'5', type:'pdf', path:'documents/time-sheets/Tom/Week-5-Tom.pdf'},
                {name:'6', type:'pdf', path:'documents/time-sheets/Tom/Week-6-Tom.pdf'},
                {name:'7', type:'pdf', path:'documents/time-sheets/Tom/Week-7-Tom.pdf'},
                {name:'8', type:'pdf', path:'documents/time-sheets/Tom/Week-8-Tom.pdf'},
                {name:'9', type:'pdf', path:'documents/time-sheets/Tom/Week-9-Tom.pdf'},
                {name:'10', type:'pdf', path:'documents/time-sheets/Tom/Week-10-Tom.pdf'},
                {name:'11', type:'pdf', path:'documents/time-sheets/Tom/Week-11-Tom.pdf'},
                {name:'12', type:'pdf', path:'documents/time-sheets/Tom/Week-12-Tom.pdf'},
                {name:'13', type:'pdf', path:'documents/time-sheets/Tom/Week-13-Tom.pdf'},
                {name:'14', type:'pdf', path:'documents/time-sheets/Tom/Week-14-Tom.pdf'},
                {name:'15', type:'pdf', path:'documents/time-sheets/Tom/Week-15-Tom.pdf'},
                {name:'16', type:'pdf', path:'documents/time-sheets/Tom/Week-16-Tom.pdf'},
                {name:'17', type:'pdf', path:'documents/time-sheets/Tom/Week-17-Tom.pdf'},
                {name:'18', type:'pdf', path:'documents/time-sheets/Tom/Week-18-Tom.pdf'},
                {name:'19', type:'pdf', path:'documents/time-sheets/Tom/Week-19-Tom.pdf'},
                {name:'20', type:'pdf', path:'documents/time-sheets/Tom/Week-20-Tom.pdf'},
                {name:'21', type:'pdf', path:'documents/time-sheets/Tom/Week-21-Tom.pdf'},
                {name:'22', type:'pdf', path:'documents/time-sheets/Tom/Week-22-Tom.pdf'},
            ]},
        {name:'Ollie', children: [
                {name:'2', type:'pdf', path:'documents/time-sheets/Ollie/Week2.pdf'},
                {name:'3', type:'pdf', path:'documents/time-sheets/Ollie/Week3.pdf'},
                {name:'4', type:'pdf', path:'documents/time-sheets/Ollie/Week4.pdf'},
                {name:'5', type:'pdf', path:'documents/time-sheets/Ollie/Week5.pdf'},
                {name:'6', type:'pdf', path:'documents/time-sheets/Ollie/Week6.pdf'},
                {name:'7', type:'pdf', path:'documents/time-sheets/Ollie/Week7.pdf'},
                {name:'8', type:'pdf', path:'documents/time-sheets/Ollie/Week8.pdf'},
                {name:'9', type:'pdf', path:'documents/time-sheets/Ollie/Week9.pdf'},
                {name:'10', type:'pdf', path:'documents/time-sheets/Ollie/Week10.pdf'},
                {name:'11', type:'pdf', path:'documents/time-sheets/Ollie/Week11.pdf'},
                {name:'12', type:'pdf', path:'documents/time-sheets/Ollie/Week12.pdf'},
                {name:'13', type:'pdf', path:'documents/time-sheets/Ollie/Week13.pdf'},
                {name:'14', type:'pdf', path:'documents/time-sheets/Ollie/Week14.pdf'},
                {name:'15', type:'pdf', path:'documents/time-sheets/Ollie/Week15.pdf'},
                {name:'16', type:'pdf', path:'documents/time-sheets/Ollie/Week16.pdf'},
                {name:'17', type:'pdf', path:'documents/time-sheets/Ollie/Week17.pdf'},
                {name:'18', type:'pdf', path:'documents/time-sheets/Ollie/Week18.pdf'},
                {name:'19', type:'pdf', path:'documents/time-sheets/Ollie/Week19.pdf'},
                {name:'20', type:'pdf', path:'documents/time-sheets/Ollie/Week20.pdf'},
                {name:'21', type:'pdf', path:'documents/time-sheets/Ollie/Week21.pdf'},
                {name:'22', type:'pdf', path:'documents/time-sheets/Ollie/Week22.pdf'},
            ]},
        {name: 'Liam', children:[
                {name:'2', type:'pdf', path:'documents/time-sheets/Liam/Week-2-Liam.pdf'},
                {name:'3', type:'pdf', path:'documents/time-sheets/Liam/Week-3-Liam.pdf'},
                {name:'4', type:'pdf', path:'documents/time-sheets/Liam/Week-4-Liam.pdf'},
                {name:'5', type:'pdf', path:'documents/time-sheets/Liam/Week-5-Liam.pdf'},
                {name:'6', type:'pdf', path:'documents/time-sheets/Liam/Week-6-Liam.pdf'},
                {name:'7', type:'pdf', path:'documents/time-sheets/Liam/Week-7-Liam.pdf'},
                {name:'8', type:'pdf', path:'documents/time-sheets/Liam/Week-8-Liam.pdf'},
                {name:'9', type:'pdf', path:'documents/time-sheets/Liam/Week-9-Liam.pdf'},
                {name:'10', type:'pdf', path:'documents/time-sheets/Liam/Week-10-Liam.pdf'},
                {name:'11', type:'pdf', path:'documents/time-sheets/Liam/Week-11-Liam.pdf'},
                {name:'12', type:'pdf', path:'documents/time-sheets/Liam/Week-12-Liam.pdf'},
                {name:'13', type:'pdf', path:'documents/time-sheets/Liam/Week-13-Liam.pdf'},
                {name:'14', type:'pdf', path:'documents/time-sheets/Liam/Week-14-Liam.pdf'},
                {name:'15', type:'pdf', path:'documents/time-sheets/Liam/Week-15-Liam.pdf'},
                {name:'16', type:'pdf', path:'documents/time-sheets/Liam/Week-16-Liam.pdf'},
                {name:'17', type:'pdf', path:'documents/time-sheets/Liam/Week-17-Liam.pdf'},
                {name:'18', type:'pdf', path:'documents/time-sheets/Liam/Week-18-Liam.pdf'},
                {name:'19', type:'pdf', path:'documents/time-sheets/Liam/Week-19-Liam.pdf'},
                {name:'20', type:'pdf', path:'documents/time-sheets/Liam/Week-20-Liam.pdf'},
                {name:'21', type:'pdf', path:'documents/time-sheets/Liam/Week-21-Liam.pdf'},
                {name:'22', type:'pdf', path:'documents/time-sheets/Liam/Week-22-Liam.pdf'},
            ]},
        {name:'Lauren', children: [
                {name:'2', type:'pdf', path:'documents/time-sheets/Lauren/week2.pdf'},
                {name:'3', type:'pdf', path:'documents/time-sheets/Lauren/week3.pdf'},
                {name:'4', type:'pdf', path:'documents/time-sheets/Lauren/week4.pdf'},
                {name:'5', type:'pdf', path:'documents/time-sheets/Lauren/week5.pdf'},
                {name:'6', type:'pdf', path:'documents/time-sheets/Lauren/week6.pdf'},
                {name:'7', type:'pdf', path:'documents/time-sheets/Lauren/week7.pdf'},
                {name:'8', type:'pdf', path:'documents/time-sheets/Lauren/week8.pdf'},
                {name:'9', type:'pdf', path:'documents/time-sheets/Lauren/week9.pdf'},
                {name:'10', type:'pdf', path:'documents/time-sheets/Lauren/week10.pdf'},
                {name:'11', type:'pdf', path:'documents/time-sheets/Lauren/week11.pdf'},
                {name:'12', type:'pdf', path:'documents/time-sheets/Lauren/week12.pdf'},
                {name:'13', type:'pdf', path:'documents/time-sheets/Lauren/week13.pdf'},
                {name:'14', type:'pdf', path:'documents/time-sheets/Lauren/Week14.pdf'},
                {name:'15', type:'pdf', path:'documents/time-sheets/Lauren/WeekSu_1.pdf'},
                {name:'16', type:'pdf', path:'documents/time-sheets/Lauren/WeekSu_2.pdf'},
                {name:'17', type:'pdf', path:'documents/time-sheets/Lauren/WeekSu_3.pdf'},
                {name:'18', type:'pdf', path:'documents/time-sheets/Lauren/WeekSu_4.pdf'},
                {name:'19', type:'pdf', path:'documents/time-sheets/Lauren/WeekSu_5.pdf'},
                {name:'20', type:'pdf', path:'documents/time-sheets/Lauren/WeekSu_6.pdf'},
                {name:'21', type:'pdf', path:'documents/time-sheets/Lauren/WeekSu_7.pdf'},
                {name:'22', type:'pdf', path:'documents/time-sheets/Lauren/WeekSu_8.pdf'},
            ]},
        {name:'Jon', children: [
                {name:'2', type:'pdf', path:'documents/time-sheets/Jon/week2.pdf'},
                {name:'3', type:'pdf', path:'documents/time-sheets/Jon/week3.pdf'},
                {name:'4', type:'pdf', path:'documents/time-sheets/Jon/week4.pdf'},
                {name:'5', type:'pdf', path:'documents/time-sheets/Jon/week5.pdf'},
                {name:'6', type:'pdf', path:'documents/time-sheets/Jon/week6.pdf'},
                {name:'7', type:'pdf', path:'documents/time-sheets/Jon/week7.pdf'},
                {name:'8', type:'pdf', path:'documents/time-sheets/Jon/week8.pdf'},
                {name:'9', type:'pdf', path:'documents/time-sheets/Jon/week9.pdf'},
                {name:'10', type:'pdf', path:'documents/time-sheets/Jon/week10.pdf'},
                {name:'11', type:'pdf', path:'documents/time-sheets/Jon/week11.pdf'},
                {name:'12', type:'pdf', path:'documents/time-sheets/Jon/week12.pdf'},
                {name:'13', type:'pdf', path:'documents/time-sheets/Jon/week13.pdf'},
                {name:'14', type:'pdf', path:'documents/time-sheets/Jon/week14.pdf'},
                {name:'15', type:'pdf', path:'documents/time-sheets/Jon/week15.pdf'},
                {name:'16', type:'pdf', path:'documents/time-sheets/Jon/week16.pdf'},
                {name:'17', type:'pdf', path:'documents/time-sheets/Jon/week17.pdf'},
                {name:'18', type:'pdf', path:'documents/time-sheets/Jon/week18.pdf'},
                {name:'19', type:'pdf', path:'documents/time-sheets/Jon/week19.pdf'},
                {name:'20', type:'pdf', path:'documents/time-sheets/Jon/week20.pdf'},
                {name:'21', type:'pdf', path:'documents/time-sheets/Jon/week21.pdf'},
                {name:'22', type:'pdf', path:'documents/time-sheets/Jon/week22.pdf'},
            ]},
        {name:'James', children: [{name:'2', type:'pdf', path:'documents/time-sheets/James/Week-2-James.pdf'},
                {name:'3', type:'pdf', path:'documents/time-sheets/James/Week-3-James.pdf'},
                {name:'4', type:'pdf', path:'documents/time-sheets/James/Week-4-James.pdf'},
                {name:'5', type:'pdf', path:'documents/time-sheets/James/Week-5-James.pdf'},
                {name:'6', type:'pdf', path:'documents/time-sheets/James/Week-6-James.pdf'},
                {name:'7', type:'pdf', path:'documents/time-sheets/James/Week-7-James.pdf'},
                {name:'8', type:'pdf', path:'documents/time-sheets/James/Week-8-James.pdf'},
                {name:'9', type:'pdf', path:'documents/time-sheets/James/Week-9-James.pdf'},
                {name:'10', type:'pdf', path:'documents/time-sheets/James/Week-10-James.pdf'},
                {name:'11', type:'pdf', path:'documents/time-sheets/James/Week-11-James.pdf'},
                {name:'12', type:'pdf', path:'documents/time-sheets/James/Week-12-James.pdf'},
                {name:'13', type:'pdf', path:'documents/time-sheets/James/Week-13-James.pdf'},
                {name:'14', type:'pdf', path:'documents/time-sheets/James/Week-14-James.pdf'},
                {name:'15', type:'pdf', path:'documents/time-sheets/James/Week-15-James.pdf'},
                {name:'16', type:'pdf', path:'documents/time-sheets/James/Week-16-James.pdf'},
                {name:'17', type:'pdf', path:'documents/time-sheets/James/Week-17-James.pdf'},
                {name:'18', type:'pdf', path:'documents/time-sheets/James/Week-18-James.pdf'},
                {name:'19', type:'pdf', path:'documents/time-sheets/James/Week-19-James.pdf'},
                {name:'20', type:'pdf', path:'documents/time-sheets/James/Week-20-James.pdf'},
                {name:'21', type:'pdf', path:'documents/time-sheets/James/Week-21-James.pdf'},
                {name:'22', type:'pdf', path:'documents/time-sheets/James/Week-22-James.pdf'},
            ]},
    ],
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
    "Contracts": {content: require('../documents/contracts/contracts.md'), children: [
            {name: "The Legba Company", type: 'pdf', path:'documents/contracts/g1.pdf'},
            {name: "York Software Development LTD",
                content: require('../documents/contracts/ysd.md'),
                children: [
                    {name:'Contract', type: 'pdf', path:'documents/contracts/g2.pdf'},
                    {name:'Client Requirements', type: 'pdf', path:'documents/contracts/clientrequirmentsspecs.pdf'},
                    {name:'Development Plan', type: 'pdf', path:'documents/contracts/developmentplan.pdf'},
                ]},
            {name: "SImpLe", type: 'pdf', path:'documents/contracts/g5.pdf'},
        ]},
    "Project-Wide Standards": {name: 'PWS', type:'iframe', path:'documents/pws/index.html'},
    "Marketing": {children: [
            {name:'Marketing Report', type:'pdf', path:'documents/Marketing/marketresearchandmarketingreport.pdf'},
            {name:'Video', type:'mp4', path:'documents/videos/marketing.mp4'},
        ]},
    "Code": "Test",
    "Individual Reports": {
        children: [
            {name: 'Jon', type:'pdf', path:'documents/individual-reports/jonathantrain.pdf'},
            {name: 'Lauren', type:'pdf', path:'documents/individual-reports/Lauren.pdf'},
            {name: 'Ollie', type:'pdf', path:'documents/individual-reports/Ollie.pdf'},
            {name: 'Tom', type:'pdf', path:'documents/individual-reports/Tom.pdf'},
        ],
    },
    "References": require('../documents/references.md'),
}