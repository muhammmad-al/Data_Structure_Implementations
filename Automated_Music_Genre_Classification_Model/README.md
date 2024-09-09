# Music Genre Classification

This project implements a music genre classification system using a feedforward neural network. The system is built with PyTorch and Keras and utilizes audio feature extraction to classify music into different genres. The system has a 75% accuracy rating in music genre classification tasks (exceeding the human benchmark of 70%). 

## Project Structure

The project includes the following main functions:

- **load_data**: Load and prepare the dataset from a CSV file.
- **preprocess_data**: Normalize features and encode labels.
- **split_data**: Split the dataset into training and testing sets.
- **create_model**: Build the neural network model.
- **train_and_evaluate**: Train the model and evaluate its performance.
- **extract_features**: Extract audio features from an audio file.
- **preprocess_and_predict**: Preprocess audio file and predict its genre.
- **load_and_evaluate_individual**: Load the trained model and evaluate individual audio files.

## Google Colab Notebook

This project is designed to be run on Google Colab, leveraging the GTZAN dataset for music genre classification. The dataset is loaded from Google Drive, and the entire workflow, including data preprocessing, model training, and evaluation, is implemented in the notebook.
