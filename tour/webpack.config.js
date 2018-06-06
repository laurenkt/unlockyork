/* eslint-disable */
const path    = require('path')
const webpack = require('webpack')
const HtmlPlugin = require('html-webpack-plugin')
const TidyHtmlPlugin = require('tidy-html-webpack-plugin')
const CopyWebpackPlugin = require('copy-webpack-plugin');

module.exports = {
	entry: path.resolve(__dirname, 'src', 'index.js'),
	output: {
		filename: 'bundle.js',
		path: path.resolve(__dirname, 'dist'),
	},
	// Don't bundle these into the output
	externals: {},
	// Load any js files through babel for polyfills etc
	module: {
		rules: [
			{
				test: /\.js$/,
				exclude: /node_modules/,
				use: [
					'babel-loader',
				],
			},
			{
				test: /\.css$/,
				use: [
					'style-loader',
					'css-loader'
				],
			},
            {
                test: /\.jpg$/,
                loader: 'file-loader',
            },
			{
				test: /\.png$/,
				loader: 'file-loader',
			},
			{
				test: /\.(ttf|eot|woff|woff2|svg|otf)$/,
				loader: 'file-loader',
				options: {
					name: 'fonts/[name].[ext]',
				},
			},
			{
				test: /\.wav/,
				use: {
					loader: 'url-loader'
				}
			},
			{
				test: /\.scss$/,
				use: [
					'style-loader',
					'css-loader',
					'sass-loader',
				],
			},
			{
				test: /\.md$/,
				use: [
					'html-loader',
					'markdown-loader',
				]
			},
		]
	},
	// Hot module reloading for CSS etc
	plugins: [
		new webpack.HotModuleReplacementPlugin(),
		new HtmlPlugin({template: 'src/index.html'}),
		new TidyHtmlPlugin(),
        new CopyWebpackPlugin([{from: 'documents', to: 'documents'}]),
		//new CopyWebpackPlugin([{from: 'src/assets/**/*', to: 'assets/[name].[ext]'}]),
		//new CopyWebpackPlugin([{from: 'report/audio', to: 'audio'}]),
	],
	devtool: 'inline-source-map',
	devServer: {
		contentBase: path.join(__dirname, 'dist'),
		hot: true,              // Enable HMR
		watchContentBase: true, // Needed to auto update when index.html changes
	},
}

