USE MASTER 
GO

DROP DATABASE if exists eRecruitment
go 

CREATE DATABASE eRecruitment
go 

use eRecruitment
go

-- 1
CREATE TABLE [Role]
(
	[role_id] int Identity(1,1) PRIMARY KEY NOT NULL,
	[role_name] NVARCHAR(100) NOT NULL

)
GO

INSERT INTO [Role]
VALUES
(N'Admin'),(N'HR Staff'),(N'Interviewer'), (N'Candidate'), (N'Member')
GO

-- 2
CREATE TABLE [User]
(
	[email] NVARCHAR(60) PRIMARY KEY NOT NULL,
	[name] NVARCHAR(60) NOT NULL,
	[role_id] int FOREIGN KEY REFERENCES dbo.[Role] DEFAULT(5) not null,
	[phone] varchar(11),
	[address] nvarchar(200)
)
GO

CREATE TABLE [User_cv]
(
	[id] int identity(1,1) PRIMARY KEY,
	[email] NVARCHAR(60) FOREIGN KEY REFERENCES dbo.[User] NOT NULL,
	[can_cv] NVARCHAR(200) NOT NULL,
	[date] Date NOT NULL
)
GO

CREATE TABLE [Notification]
(
	[nId] int IDENTITY(1,1) PRIMARY KEY NOT NULL,
	[email] NVARCHAR(60) FOREIGN KEY REFERENCES dbo.[User] not null,
	[title] NVARCHAR(100) not null,
	[content] text not null,
	[link_title] NVARCHAR(100),
	[link] NVARCHAR(200),
	[date] datetime not null,
	[isRead] bit DEFAULT (0) not null
)
GO

/*
INSERT INTO [Users]
VALUES
(),
GO
*/

-- 3
CREATE TABLE [Major]
(
	[major_id] int IDENTITY(1,1) PRIMARY KEY NOT NULL,
	[major_name] NVARCHAR(100) NOT NULL,
)
GO



INSERT INTO [Major]
VALUES
('Marketing'),('Logistic'),('Data Science and Analytics'),('Information Technology'),('Graphic Design'),
('Engineering'),('Risk Manager'),('Economic Finance'),('Business Intelligence and Development'),
('Operations')
GO


-- 4
CREATE TABLE [Interviewer]
(
	[inter_id] CHAR(3) PRIMARY KEY NOT NULL, --I00
	[email] NVARCHAR(60) FOREIGN KEY REFERENCES dbo.[User] NOT NULL ,
	[major_id] int FOREIGN KEY REFERENCES dbo.[Major] NOT NULL,
	[isAvailable] bit DEFAULT (0) NOT NULL
)
GO

/*
INSERT INTO [Interviewers]
VALUES
(),
GO
*/

-- 5

CREATE TABLE [Level]
(
	[level_id]  int IDENTITY(1,1) PRIMARY KEY NOT NULL,
	[level_name] NVARCHAR(10)  NOT NULL
)
GO

INSERT INTO [Level]
VALUES
('Intern'),('Fresher'),('Junior'),('Seninor'),('Manager'),('Leader')


-- 6 
CREATE TABLE [Job]
(
	[job_id] CHAR(4) PRIMARY KEY NOT NULL, --J000
	[job_name] NVARCHAR(50) NOT NULL,
	[major_id] int FOREIGN KEY REFERENCES dbo.[Major] NOT NULL,
	[job_vacancy] int NOT NULL,
	[job_description] text,
	[level_id] int FOREIGN KEY REFERENCES dbo.[Level] NOT NULL,
	[salary] FLOAT not null,
	[post_date] DATE not null
)
GO
/*
INSERT INTO [Jobs]
VALUES
(),
GO
*/
-- 7
CREATE TABLE [Saved_job]
(
	[job_id] CHAR(4) FOREIGN KEY REFERENCES dbo.[Job] NOT NULL,
	[email] NVARCHAR(60) FOREIGN KEY REFERENCES dbo.[User] NOT NULL,
	UNIQUE([job_id],[email])
)
GO
/*
INSERT INTO [Saved_jobs]
VALUES
(),
GO
*/
-- 8
CREATE TABLE [Candidate]
(

	[can_id] CHAR(4) PRIMARY KEY NOT NULL, --C000
	[job_id] CHAR(4) FOREIGN KEY REFERENCES dbo.[Job] NOT NULL,
	[email] NVARCHAR(60) FOREIGN KEY REFERENCES dbo.[User] NOT NULL,
	[can_cv] NVARCHAR(200) NOT NULL,-- comment 
	[score] FLOAT,
	[isStatus] int DEFAULT (0) NOT NULL,
	/*	Note: 
		0 : HR hasn't accepted
		1 : HR had accepted
		2 : has Tested
		3 : has scheduled
		4 : has been Interviewed
		5 : Accepted
	*/
	UNIQUE([job_id],[email])
)
GO
/*
INSERT INTO [Member]
VALUES
(),
GO
*/
-- 9

/*
CREATE TABLE [Skill]
(
	[skill_id] int Identity(1,1) PRIMARY KEY NOT NULL,
	[skill_name] NVARCHAR(100) UNIQUE NOT NULL,
	[skill_description] text
)
GO

INSERT INTO [Skill] ([skill_name])
VALUES
('JavaSript'),('Java'),('Python'),('C++'),('C#'),('UI/UX'),('HTML'),('Testing'),('React Native'),('Angular'),('Mobile Develope'),('Ruby'),('Rust'),('Golang'),('TypeScript'),('BrainFuck'),
('PHP'),('Unity'),('Unreal Engine'),
('Data Visualization'),('Data Cleaning'),('MATLAB'),('SQL and NoSQL'),('Machine Learning'),('Linear Algebra and Calculus'),('Microsoft Excel'),('Critical Thinking')
GO
*/

-- 10
CREATE TABLE [Job_skill]
(

	[skill_id] int Identity(1,1) PRIMARY KEY NOT NULL,
	[job_id] CHAR(4) FOREIGN KEY REFERENCES dbo.[Job] NOT NULL,
	[detail] NVARCHAR(200) not null,
	UNIQUE([job_id],[skill_id])
)
GO
/*
INSERT INTO [Skills]
VALUES
(),
GO
*/
-- 11

CREATE TABLE [Candidate_skill]
(
	[can_id] CHAR(4) FOREIGN KEY REFERENCES dbo.[Candidate] NOT NULL,
	[skill_id] int FOREIGN KEY REFERENCES dbo.[Job_skill] NOT NULL,
	UNIQUE([can_id],[skill_id])
)
GO
/*
INSERT INTO [Member_skills]
VALUES
(),
GO
*/
-- 12
CREATE TABLE [Question]
(
	[q_id] CHAR(4) PRIMARY KEY NOT NULL, --Q000
	[questiontxt] text NOT NULL,
	[major_id] int FOREIGN KEY REFERENCES dbo.[Major] NOT NULL,
)
GO

/*
INSERT INTO [Questions]
VALUES
(),
GO
*/
-- 13
CREATE TABLE [Option]
(
	[op_id] int Identity(1,1) PRIMARY KEY NOT NULL, 
	[q_id] CHAR(4) FOREIGN KEY REFERENCES dbo.[Question] NOT NULL,
	[content] text not null,
	[isCorrect] bit DEFAULT(0) NOT NULL
)
GO
/*
INSERT INTO [Options]
VALUES
(),
GO
*/

-- 14
CREATE TABLE [Exam]
(
	[exam_id] CHAR(3) PRIMARY KEY NOT NULL, --E00
	[exam_name] NVARCHAR(30) NOT NULL,
	[major_id] int FOREIGN KEY REFERENCES dbo.[Major] NOT NULL
)
GO

/*
INSERT INTO [Exams]
VALUES
(),
GO
*/

-- 15
CREATE TABLE [Exam_question]
(
	[exam_id] CHAR(3) FOREIGN KEY REFERENCES dbo.[Exam] NOT NULL,
	[q_id] CHAR(4) FOREIGN KEY REFERENCES dbo.[Question] NOT NULL,
	UNIQUE([exam_id],[q_id])
)
GO

/*
INSERT INTO [Exam_questions]
VALUES
(),
GO
*/
-- 16
CREATE TABLE [Examination]
(
	[exam_id] CHAR(3) FOREIGN KEY REFERENCES dbo.[Exam] NOT NULL,
	[can_id] CHAR(4) FOREIGN KEY REFERENCES dbo.[Candidate] NOT NULL,
	[status] bit DEFAULT (0) not null,
	UNIQUE ([exam_id],[can_id])
)
GO

/*
INSERT INTO [Examsination]
VALUES
(),
GO
*/
-- 17


CREATE TABLE [Interviewing]
(
	[id] int IDENTITY(1,1) PRIMARY KEY NOT NULL,
	[inter_id] CHAR(3) FOREIGN KEY REFERENCES dbo.[Interviewer] NOT NULL,
	[can_id] CHAR(4) FOREIGN KEY REFERENCES dbo.[Candidate] NOT NULL,
	[date] DATETIME not null,
	[location] NVARCHAR(100) NOT NULL,
	[inter_score] INT,
	[inter_comment] text,
	[isStatus] int DEFAULT (0) not null,
	UNIQUE ([inter_id],[can_id])
)
GO

/*
INSERT INTO [Examsination]
VALUES
(),
GO
*/

CREATE TABLE [Feedback]
(
	[id] int IDENTITY(1,1) PRIMARY KEY NOT NULL,
	[email] NVARCHAR(60) NOT NULL,
	[subject] NVARCHAR (150) NOT NULL,
	[detail] NVARCHAR(400) NOT NULL,
	[date] date not null
)
GO
CREATE TABLE [Q&A_question]
(
	[qId] char(4) PRIMARY KEY NOT NULL,--Q000
	[email] NVARCHAR(60) FOREIGN KEY REFERENCES dbo.[User] NOT NULL,
	[detail] text NOT NULL,
	[datetime] datetime NOT NULL,
)
GO
CREATE TABLE [Q&A_answer]
(
	[aId] char(5) PRIMARY KEY NOT NULL,--A0000
	[email] NVARCHAR(60) FOREIGN KEY REFERENCES dbo.[User] NOT NULL,
	[qId] char(4) FOREIGN KEY REFERENCES dbo.[Q&A_question] NOT NULL,
	[detail] text NOT NULL,
	[datetime] datetime NOT NULL,
)
GO
CREATE TABLE [Report]
(
	[r_id] int IDENTITY(1,1) PRIMARY KEY NOT NULL,
	[reason] NVARCHAR(100) NOT NULL,
)
GO
INSERT INTO [Report] ([reason])
VALUES
('Unwanted commercial content or spam'),
('Pornography or sexually explicit material'),
('Child abuse'),
('Hate speech or graphic violence'),
('Promotes terrorism'),
('Harassment or bullying'),
('Suicide or self injury'),
('Misinformation')


CREATE TABLE [Report_list]
(
	[Q&A_id] char(5) not null,
	[email] NVARCHAR(60) FOREIGN KEY REFERENCES dbo.[User] NOT NULL,
	[r_id] int FOREIGN KEY REFERENCES dbo.[Report] NOT NULL,
)

GO
-- Data

USE [eRecruitment]
GO
INSERT [dbo].[User] ([email], [name], [role_id], [phone], [address]) VALUES (N'126.thienphuc@gmail.com', N'Thiện Nguyễn', 5, NULL, NULL)
GO
INSERT [dbo].[User] ([email], [name], [role_id], [phone], [address]) VALUES (N'do@gmail.com', N'Xuân Tóc Đỏ', 4, NULL, NULL)
GO
INSERT [dbo].[User] ([email], [name], [role_id], [phone], [address]) VALUES (N'duy@gmail.com', N'Đàm Xuân Duy', 4, NULL, NULL)
GO
INSERT [dbo].[User] ([email], [name], [role_id], [phone], [address]) VALUES (N'hieu@gmail.com', N'Nguyễn Minh Hiếu', 4, NULL, NULL)
GO
INSERT [dbo].[User] ([email], [name], [role_id], [phone], [address]) VALUES (N'hieunmse162040@fpt.edu.vn', N'Nguyen Minh Hieu (K16 HCM)', 5, NULL, NULL)
GO
INSERT [dbo].[User] ([email], [name], [role_id], [phone], [address]) VALUES (N'hoang@gmail.com', N'Đinh Thiên Hoàng', 4, NULL, NULL)
GO
INSERT [dbo].[User] ([email], [name], [role_id], [phone], [address]) VALUES (N'hoangthse162042@fpt.edu.vn', N'Thai Huy Hoang (K16 HCM)', 5, NULL, NULL)
GO
INSERT [dbo].[User] ([email], [name], [role_id], [phone], [address]) VALUES (N'hrstaff126@gmail.com', N'Anh HR May Mắn', 2, NULL, NULL)
GO
INSERT [dbo].[User] ([email], [name], [role_id], [phone], [address]) VALUES (N'huy@gmail.com', N'Nguyễn Quốc Huy', 4, NULL, NULL)
GO
INSERT [dbo].[User] ([email], [name], [role_id], [phone], [address]) VALUES (N'interviewer126@gmail.com', N'Interviewer Số 1', 3, NULL, NULL)
GO
INSERT [dbo].[User] ([email], [name], [role_id], [phone], [address]) VALUES (N'interviewer326@gmail.com', N'Interviewer Số 3', 3, NULL, NULL)
GO
INSERT [dbo].[User] ([email], [name], [role_id], [phone], [address]) VALUES (N'nam@gmail.com', N'Nguyễn Tuấn Nam', 4, NULL, NULL)
GO
INSERT [dbo].[User] ([email], [name], [role_id], [phone], [address]) VALUES (N'nguyenhuuthien12a2@gmail.com', N'Nguyễn Hữu Thiện', 5, NULL, NULL)
GO
INSERT [dbo].[User] ([email], [name], [role_id], [phone], [address]) VALUES (N'nguyenhuuthien9a1nbk@gmail.com', N'thien', 1, N'0332221765', NULL)
GO
INSERT [dbo].[User] ([email], [name], [role_id], [phone], [address]) VALUES (N'niem.huu.thien@gmail.com', N'Interviewer Số 2', 3, NULL, NULL)
GO
INSERT [dbo].[User] ([email], [name], [role_id], [phone], [address]) VALUES (N'thai@gmail.com', N'Nguyễn Thái', 4, NULL, NULL)
GO
INSERT [dbo].[User] ([email], [name], [role_id], [phone], [address]) VALUES (N'thiennhse162041@fpt.edu.vn', N'Nguyen Huu Thien (K16 HCM)', 5, NULL, NULL)
GO
INSERT [dbo].[User] ([email], [name], [role_id], [phone], [address]) VALUES (N'tuan@gmail.com', N'Nguyễn Anh Tuấn', 4, NULL, NULL)
GO
INSERT [dbo].[User] ([email], [name], [role_id], [phone], [address]) VALUES (N'vang@gmail.com', N'Cậu Vàng', 4, NULL, NULL)
GO
SET IDENTITY_INSERT [dbo].[User_cv] ON 
GO
INSERT [dbo].[User_cv] ([id], [email], [can_cv], [date]) VALUES (1, N'thiennhse162041@fpt.edu.vn', N'1.pdf', CAST(N'2022-11-11' AS Date))
GO
INSERT [dbo].[User_cv] ([id], [email], [can_cv], [date]) VALUES (3, N'126.thienphuc@gmail.com', N'1.pdf', CAST(N'2022-11-11' AS Date))
GO
SET IDENTITY_INSERT [dbo].[User_cv] OFF
GO
SET IDENTITY_INSERT [dbo].[Notification] ON 
GO
INSERT [dbo].[Notification] ([nId], [email], [title], [content], [link_title], [link], [date], [isRead]) VALUES (1, N'thiennhse162041@fpt.edu.vn', N'Accepted Resume', N'<p>Your Resume already accepted.</p>', NULL, NULL, CAST(N'2022-11-11T13:19:16.000' AS DateTime), 0)
GO
INSERT [dbo].[Notification] ([nId], [email], [title], [content], [link_title], [link], [date], [isRead]) VALUES (2, N'thiennhse162041@fpt.edu.vn', N'Accepted Resume', N'<p>Your Resume already accepted.</p>', NULL, NULL, CAST(N'2022-11-11T13:34:40.000' AS DateTime), 0)
GO
SET IDENTITY_INSERT [dbo].[Notification] OFF
GO
INSERT [dbo].[Interviewer] ([inter_id], [email], [major_id], [isAvailable]) VALUES (N'I01', N'interviewer126@gmail.com', 1, 1)
GO
INSERT [dbo].[Interviewer] ([inter_id], [email], [major_id], [isAvailable]) VALUES (N'I02', N'niem.huu.thien@gmail.com', 1, 1)
GO
INSERT [dbo].[Interviewer] ([inter_id], [email], [major_id], [isAvailable]) VALUES (N'I03', N'interviewer326@gmail.com', 1, 1)
GO
INSERT [dbo].[Job] ([job_id], [job_name], [major_id], [job_vacancy], [job_description], [level_id], [salary], [post_date]) VALUES (N'C001', N'Game Developer', 1, 12, N'Video game developers, also known as game developers, are responsible for designing and developing video games for PC, console, and mobile applications. Their job is to code the base engine from the ideas of the design team. They may also be involved in character design, level design, animation, and unit testing.', 3, 1200, CAST(N'2022-11-04' AS Date))
GO
INSERT [dbo].[Job] ([job_id], [job_name], [major_id], [job_vacancy], [job_description], [level_id], [salary], [post_date]) VALUES (N'C002', N'Web Developer', 1, 21, N'The web developer, also called a web designer or webmaster, is the person who builds, adjusts, and/or maintains it. Web developers may work for small companies, large corporations, or as freelancers, using technical and creative skills to create functional websites.', 4, 5000, CAST(N'2022-11-04' AS Date))
GO
INSERT [dbo].[Job] ([job_id], [job_name], [major_id], [job_vacancy], [job_description], [level_id], [salary], [post_date]) VALUES (N'C003', N'Oracle Developer', 1, 30, N'Oracle developer tasks are to perform the design and development of Oracle applications permitting to project requirements. Typically, the design, code, and sustain the Oracle systems based on established standards.', 3, 2500, CAST(N'2022-11-04' AS Date))
GO
INSERT [dbo].[Job] ([job_id], [job_name], [major_id], [job_vacancy], [job_description], [level_id], [salary], [post_date]) VALUES (N'C004', N'Java Developer', 1, 10, N'A Java Developer is responsible for designing, implementing, and maintaining Java-based software and applications, contributing to all stages of the software development lifecycle. They thoroughly analyze user requirements, envision system features, and define application functionality.', 4, 10000, CAST(N'2022-11-10' AS Date))
GO
INSERT [dbo].[Job] ([job_id], [job_name], [major_id], [job_vacancy], [job_description], [level_id], [salary], [post_date]) VALUES (N'C005', N'IOS Developer', 5, 21, N'iOS developers design and build applications for mobile devices running Apple''s iOS operating software. They are responsible for designing and coding the base application, ensuring the quality of the application, fixing application bugs, maintaining the code, and implementing application updates.', 5, 20000, CAST(N'2022-11-10' AS Date))
GO
INSERT [dbo].[Job] ([job_id], [job_name], [major_id], [job_vacancy], [job_description], [level_id], [salary], [post_date]) VALUES (N'C007', N'Python Developer', 4, 0, N'Python developers are computer programmers who specialize in writing server-side web application logic. Their job is to use the Python programming language to develop, debug, and implement application projects.', 3, 2000, CAST(N'2022-11-11' AS Date))
GO
INSERT [dbo].[Job] ([job_id], [job_name], [major_id], [job_vacancy], [job_description], [level_id], [salary], [post_date]) VALUES (N'C015', N'SQL Developer', 9, 5, N'SQL developers are responsible for all aspects of designing, creating and maintaining databases, including: Building databases and validating their stability and efficiency. Creating program views, functions and stored procedures.', 6, 50000, CAST(N'2022-11-11' AS Date))
GO
INSERT [dbo].[Candidate] ([can_id], [job_id], [email], [can_cv], [score], [isStatus]) VALUES (N'C001', N'C001', N'hoangthse162042@fpt.edu.vn', N'1.pdf', 6, 2)
GO
INSERT [dbo].[Candidate] ([can_id], [job_id], [email], [can_cv], [score], [isStatus]) VALUES (N'C002', N'C001', N'126.thienphuc@gmail.com', N'2.pdf', 4, 2)
GO
INSERT [dbo].[Candidate] ([can_id], [job_id], [email], [can_cv], [score], [isStatus]) VALUES (N'C003', N'C001', N'nguyenhuuthien12a2@gmail.com', N'3.pdf', 9, 2)
GO
INSERT [dbo].[Candidate] ([can_id], [job_id], [email], [can_cv], [score], [isStatus]) VALUES (N'C005', N'C001', N'tuan@gmail.com', N'5.pdf', 8, 2)
GO
INSERT [dbo].[Candidate] ([can_id], [job_id], [email], [can_cv], [score], [isStatus]) VALUES (N'C006', N'C001', N'nam@gmail.com', N'6.pdf', 7, 2)
GO
INSERT [dbo].[Candidate] ([can_id], [job_id], [email], [can_cv], [score], [isStatus]) VALUES (N'C007', N'C001', N'huy@gmail.com', N'7.pdf', 6, 2)
GO
INSERT [dbo].[Candidate] ([can_id], [job_id], [email], [can_cv], [score], [isStatus]) VALUES (N'C008', N'C001', N'hieu@gmail.com', N'8.pdf', 4, 2)
GO
INSERT [dbo].[Candidate] ([can_id], [job_id], [email], [can_cv], [score], [isStatus]) VALUES (N'C009', N'C001', N'hoang@gmail.com', N'9.pdf', 7, 2)
GO
INSERT [dbo].[Candidate] ([can_id], [job_id], [email], [can_cv], [score], [isStatus]) VALUES (N'C010', N'C001', N'duy@gmail.com', N'10.pdf', 7, 2)
GO
INSERT [dbo].[Candidate] ([can_id], [job_id], [email], [can_cv], [score], [isStatus]) VALUES (N'C012', N'C015', N'thiennhse162041@fpt.edu.vn', N'1.pdf', NULL, 1)
GO
SET IDENTITY_INSERT [dbo].[Job_skill] ON 
GO
INSERT [dbo].[Job_skill] ([skill_id], [job_id], [detail]) VALUES (1, N'C001', N'Creativity.')
GO
INSERT [dbo].[Job_skill] ([skill_id], [job_id], [detail]) VALUES (2, N'C001', N'A passion for video games.')
GO
INSERT [dbo].[Job_skill] ([skill_id], [job_id], [detail]) VALUES (3, N'C002', N'Java')
GO
INSERT [dbo].[Job_skill] ([skill_id], [job_id], [detail]) VALUES (4, N'C002', N'C')
GO
INSERT [dbo].[Job_skill] ([skill_id], [job_id], [detail]) VALUES (5, N'C003', N'Teamwork and collaborative skills.')
GO
INSERT [dbo].[Job_skill] ([skill_id], [job_id], [detail]) VALUES (6, N'C003', N'Verbal and written communication.')
GO
INSERT [dbo].[Job_skill] ([skill_id], [job_id], [detail]) VALUES (7, N'C004', N'Proficiency in Java, with a good understanding of its ecosystems.')
GO
INSERT [dbo].[Job_skill] ([skill_id], [job_id], [detail]) VALUES (8, N'C004', N'Sound knowledge of Object-Oriented Programming (OOP) Patterns and Concepts.')
GO
INSERT [dbo].[Job_skill] ([skill_id], [job_id], [detail]) VALUES (9, N'C005', N'Work experience as an app developer.')
GO
INSERT [dbo].[Job_skill] ([skill_id], [job_id], [detail]) VALUES (10, N'C005', N'Experience in Objective-C, Swift, Xcode, and Cocoa Touch.')
GO
INSERT [dbo].[Job_skill] ([skill_id], [job_id], [detail]) VALUES (31, N'C015', N'Proficiency in SQL.')
GO
INSERT [dbo].[Job_skill] ([skill_id], [job_id], [detail]) VALUES (32, N'C015', N'Problem solving skills.')
GO
INSERT [dbo].[Job_skill] ([skill_id], [job_id], [detail]) VALUES (39, N'C007', N'CSS, HTML and JavaScript.')
GO
INSERT [dbo].[Job_skill] ([skill_id], [job_id], [detail]) VALUES (40, N'C007', N'Python Frameworks.')
GO
SET IDENTITY_INSERT [dbo].[Job_skill] OFF
GO